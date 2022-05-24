package com.tcloudsoft.auth.provider.config;

import com.tcloudsoft.auth.provider.model.Log;
import com.tcloudsoft.auth.provider.service.LogService;
import com.tcloudsoft.auth.provider.service.ManageService;
import com.tcloudsoft.auth.provider.vo.AuthResult;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.common.UserVo;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.utils.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 监听controller所有方法请求的相关信息
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class HttpLogAspect {
    //访问开始时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    //日志对象（线程安全）
    ThreadLocal<Log> logInfo = new ThreadLocal<>();

    @Resource
    private LogService logService;
    @Resource
    ManageService manageService;

    @Pointcut("@annotation(com.tcloudsoft.utils.annotation.HttpConsole)")
    public void log(){}

    /**
     * 记录请求的路径等信息
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        String url = "";
        String method = "";
        String ip = "";
        String class_method="";
        String arg="";
        String username="";
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            Log logs = new Log();
            // 记录请求url
            url= request.getRequestURL().toString();
            logs.setUrl(request.getRequestURL().toString());
            //记录请求的方法 get/post/deleted
            method=request.getMethod();
            logs.setMethod(request.getMethod());
            //记录请求的IP地址
            ip= HttpUtils.getIpAddress(request);
            logs.setIp(ip);
            //记录请求的接口
            class_method=joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            logs.setClassName(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            //获取请求头，拿到操作人信息
            String token= attributes.getRequest().getHeader("x-auth-token");
            AuthResult authResult = manageService.findByToken(token);
            UserVo userVo = authResult.getUser();
            username= TcloudUtils.isEmpty(userVo.getName())?"未知":userVo.getName();
            logs.setUsername(username);

            //获取请求携带的参数
            String str = "";
            if("POST".equals(request.getMethod())||"DELETE".equals(request.getMethod())){
                Object[] obj = joinPoint.getArgs();
                List<Object> list =new ArrayList<>(Arrays.asList(obj));
                if(TcloudUtils.isNotEmpty(token)&&list.size()>0&&list.contains(token)){//如果请求头中包含有token
                    list.remove(token);
                }
                str= list.toString();
            }else if("GET".equals(request.getMethod())){
                Enumeration<String> enu = request.getParameterNames();
                //访问方法是get，但是传参是斜杆传参
                if(!enu.hasMoreElements()){
                    Object[] obj = joinPoint.getArgs();
                    List<Object> list =new ArrayList<>(Arrays.asList(obj));
                    if(TcloudUtils.isNotEmpty(token)&&list.size()>0&&list.contains(token)){//如果请求头中包含有token
                        //将请求头去除掉
                        list.remove(token);
                    }
                    str= list.toString();
                }
                while (enu.hasMoreElements()) {
                    String paraName = enu.nextElement();
                    str=str+""+paraName+":"+request.getParameter(paraName)+",";
                }
            }
            arg = str;
            logs.setArgs(str);
            logs.setCreatorTime(new Date());
            //保存日志信息到局部threadLocal中
            logInfo.set(logs);
            startTime.set(System.currentTimeMillis());
        }catch (Exception e){
            //创建一个新的日志对象，并存入已有的信息
            Log errLog = new Log();
            errLog.setUrl(url);
            errLog.setMethod(method);
            errLog.setIp(ip);
            errLog.setClassName(class_method);
            errLog.setArgs(arg);
            errLog.setCreatorTime(new Date());
            errLog.setUsername(username);
            errLog.setDescription("日志执行有误，数据可能存在丢失");
            logInfo.set(errLog);
            startTime.set(System.currentTimeMillis());
            log.error("日志执行出错(doBefore)",e);
        }
    }

    /**
     * 请求完成后，记录请求耗时
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        try {
            //获取此次请求的日志
            Log logs = logInfo.get();
            if(TcloudUtils.isNotEmpty(logs)){
                if (null == object) {
                    logs.setSuccess(1);//失败
                } else {
                    ResponseData responseData = (ResponseData) object;
                    if(responseData.isSuccess()){
                        logs.setSuccess(0);//成功
                    }else{
                        logs.setSuccess(1);//主体方法执行失败
                    }
                }
                logs.setRequestTime(System.currentTimeMillis() - startTime.get());
                logService.save(logs);
            }
        }catch (Exception e){
            log.error("日志出错(doAfterReturn)",e);
            Log logs = logInfo.get();
            logs.setDescription("日志执行有误，数据可能存在丢失");
            logs.setSuccess(2);
            logs.setRequestTime(System.currentTimeMillis() - startTime.get());
            logService.save(logs);
        }
    }
}
