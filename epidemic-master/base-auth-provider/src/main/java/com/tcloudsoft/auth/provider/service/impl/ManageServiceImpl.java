package com.tcloudsoft.auth.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Account;
import com.tcloudsoft.auth.provider.model.Manage;
import com.tcloudsoft.auth.provider.mapper.ManageMapper;
import com.tcloudsoft.auth.provider.service.ManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.auth.provider.utils.RedisKeys;
import com.tcloudsoft.auth.provider.vo.AuthQuery;
import com.tcloudsoft.auth.provider.vo.AuthResult;
import com.tcloudsoft.redis.config.RedisService;
import com.tcloudsoft.utils.MD5PasswordEncoder;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.TokenGenerator;
import com.tcloudsoft.utils.common.UserInfo;
import com.tcloudsoft.utils.common.UserVo;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuwei
 * @since 2021-11-30
 */
@Service
public class ManageServiceImpl extends ServiceImpl<ManageMapper, Manage> implements ManageService {

    @Resource
    RedisService redisService;
    @Value("${login.failed.num}")
    Integer failedNum;
    //最大错误次数
    @Value("${login.failed.lock-time-in-seconds}")
    Long lockTimeInSeconds;
    //token缓存时间
    @Value("${login.token.expire-time-in-seconds}")
    Integer expireTimeInSeconds;
    @Resource
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public AuthResult findByToken(String token) throws IllegalArgumentException {
        if (redisService.hasKey(token)) {
            AuthResult authResult = JSONObject.toJavaObject(
                    JSON.parseObject(String.valueOf(redisService.get(token))), AuthResult.class);
            return authResult;
        } else {
            log.error(ResponseCodeEnum.C00006.getMessage());
            throw new IllegalArgumentException("凭证已失效，请重新登录");
        }
    }

    @Override
    public AuthResult auth(AuthQuery query) throws IllegalArgumentException {
        Manage user = this.findByUserName(query.getUsername());
        if (TcloudUtils.isEmpty(user)){
            throw new IllegalArgumentException("用户不存在，登录失败");
        }
        //该用户对应的错误密码输入次数在redis中存放的key值
        String userLoginFailedKey =
                RedisKeys.formatKey(RedisKeys.USER_LOGIN_FAILED_KEY, user.getId());
        // 判断用户是否输入错误次数达到上限
        Integer failedNum = 0;
        if (redisService.hasKey(userLoginFailedKey)) {//如果该key存在
            //根据key获取其value值
            failedNum = (Integer) redisService.get(userLoginFailedKey);
        }
        if (failedNum >= this.failedNum) {
            throw new IllegalArgumentException("您的账号已被锁定，请"+redisService.getExpire(userLoginFailedKey)+"后重试");//返回该错误输入次数的过期时间
        }
        if (!MD5PasswordEncoder.encode(query.getPassword()).equals(user.getPassword())){
            // 密码输入失败，开始累计失败次数
            if (redisService.hasKey(userLoginFailedKey)) {
                Integer num = (Integer) redisService.get(userLoginFailedKey);
                //替换原本的错误输入次数的值，次数增加1
                redisService.set(userLoginFailedKey, ++num, lockTimeInSeconds);//默认300秒
            } else {
                redisService.set(userLoginFailedKey, 1, lockTimeInSeconds);//默认300秒
            }
            throw new IllegalArgumentException("密码错误，您还有"+(3-failedNum)+"次错误机会，超过三次账号即被锁定");
        }
        Date now = new Date();
        UserVo userVo = new UserVo();
        //user拷贝到uservo
        BeanUtils.copyProperties(user, userVo);
        //为原日期增加秒数（获取该token最终的过期时间）
        Date expireAt = DateUtils.addSeconds(now, expireTimeInSeconds);
        // 判断用户是否已经登录
        String userLoginKey =
                RedisKeys.formatKey(RedisKeys.USER_TOKEN_KEY, query.getAppId(), user.getId());
        boolean hasKey = redisService.hasKey(userLoginKey);
        String token = null;
        try {
            UserInfo ui = new UserInfo();
            BeanUtils.copyProperties(userVo, ui);
            token = TokenGenerator.generator(ui);//生成token算法工具类
        } catch (Exception e) {
            log.error("产生token失败", e);
            throw new IllegalArgumentException("系统错误");
        }
        if (hasKey) {//如果该key已经存在了
            String oldToken = (String) redisService.get(userLoginKey);
            redisService.del(oldToken);//删除旧的token
            simpMessagingTemplate.convertAndSend("/" + query.getAppId() + "/" + user.getId(), 0);
        }
        AuthResult authResult = new AuthResult();
        authResult.setToken(token);
        authResult.setUser(userVo);
        authResult.setExpireAt(expireAt);
        authResult.setAppId(query.getAppId());
        // 将用户登录情况存放缓存（主要用来判断用户是否登录，判读登录情况）
        redisService.set(userLoginKey, token, expireTimeInSeconds);
        // 将用户信息存入缓存（主要用来通过token获取用户的信息的作用）
        redisService.set(token, JSONObject.toJSONString(authResult), expireTimeInSeconds);
        // 清除失败次数
        redisService.del(userLoginFailedKey);
        return authResult;
    }

    @Override
    public Manage findByUserName(String userName) throws IllegalArgumentException {
        QueryWrapper<Manage> query = Wrappers.query();
        query.eq("code", userName);
        try {
            Manage user = this.getOne(query);
            return user;
        } catch (Exception e) {
            log.error("用户【".concat(userName).concat("】存在多个或者不存在"), e);
            throw new IllegalArgumentException("用户不存在");
        }
    }

    @Override
    public void logout(String token) throws TcmsAuthException {
        try {
            AuthResult auth = this.findByToken(token);
            String userLoginKey =
                    RedisKeys.formatKey(RedisKeys.USER_TOKEN_KEY, auth.getAppId(), auth.getUser().getId());
            redisService.del(userLoginKey, token);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error(ResponseCodeEnum.C00006.getMessage(), e);
            throw new TcmsAuthException(ResponseCodeEnum.C00006);
        }
    }

    @Override
    public void check(String token) throws TcmsAuthException {
        AuthResult auth = this.findByToken(token);
        Date now = new Date();
        // 过期时间5分钟内，更新token过期时间
        if (now.getTime() - auth.getExpireAt().getTime() < 5 * 60 * 1000) {
            String userLoginKey =
                    RedisKeys.formatKey(RedisKeys.USER_TOKEN_KEY, auth.getAppId(), auth.getUser().getId());
            redisService.expire(userLoginKey, expireTimeInSeconds);
            redisService.expire(token, expireTimeInSeconds);
        }
    }
}
