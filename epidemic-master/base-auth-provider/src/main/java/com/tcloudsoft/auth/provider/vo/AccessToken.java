package com.tcloudsoft.auth.provider.vo;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.HashMap;
@Getter
public class AccessToken implements Serializable {
    private String grant_type = "client_credential";
    private String appid ;
    private String secret ;
    private String accessToken ;
    private long expiresTime;

    private AccessToken(String appid, String secret, String accessToken, long expiresTime) {
        this.appid = appid;
        this.secret = secret;
        this.accessToken = accessToken;
        if(expiresTime >0){
            this.expiresTime = System.currentTimeMillis() +expiresTime*1000;
        }
    }

    /*
    构建获取access token请求对象，用户唯一标识密钥
     */
    public static HashMap<String,String> requestOf(String appid,String secret){
        HashMap<String,String> request = new HashMap<>();
        request.put("appid",appid);
        request.put("secret",secret);
        request.put("grant_type","client_credential");

        return request;
    }
    /**
     *构建accesstoken对象
     */
    public static  AccessToken responseOf(String accessToken,long expiresTime){
        return new AccessToken("","",accessToken,expiresTime);
    }

    /**
     * 判断token是不是失效  true失效
     * @return
     */
    public boolean isExpired(){
        return System.currentTimeMillis() > expiresTime;

    }
}
