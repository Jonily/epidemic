/*package com.tcloudsoft.auth.provider.config;

import com.tcloudsoft.auth.provider.service.UserService;
import com.tcloudsoft.auth.provider.utils.DateSource.DBContextHolder;
import com.tcloudsoft.auth.provider.vo.AuthResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Slf4j
@Aspect
@Component
@Order(1) // 请注意：这里order一定要小于tx:annotation-driven的order，即先执行DynamicDataSourceAspectAdvice切面，再执行事务切面，才能获取到最终的数据源
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Aop {
    @Autowired
    UserService userService;

    @Around("execution(* com.tcloudsoft.auth.provider.controller.*.*(..)) ")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取用户相关数据源标识信息
        String token= sra.getRequest().getHeader("x-auth-token");
        //AuthResult authResult =  userService.findByToken(token);
        String deptId = "zbc3";

        log.info("当前数据源Id:{}", deptId);
        DBContextHolder.setDataSource(deptId);
        Object result = jp.proceed();
        //DBContextHolder.clearDataSource();
        return result;
    }
}*/
