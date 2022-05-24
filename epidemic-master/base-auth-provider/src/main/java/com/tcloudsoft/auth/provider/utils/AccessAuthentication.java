package com.tcloudsoft.auth.provider.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 接入认证
 *
 * @date: 2020-10-10 18:23
 * @Description:
 */
public class AccessAuthentication implements Serializable {

    /**
     * 将token、timestamp、nonce三个参数进行字典序排序
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密
     * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     */

    private String token;
    private String timestamp;
    private String nonce;
    private String signature;

    private AccessAuthentication(String token, String timestamp, String nonce, String signature) {
        this.token = token;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
    }

    public static AccessAuthentication of(String token, String timestamp, String nonce, String signature) {
        return new AccessAuthentication(token, timestamp, nonce, signature);
    }

    /**
     * 接入验证
     *
     * @return 是否验证通过
     */
    public boolean checkSignature() {
        // 1、将token、timestamp、nonce三个参数进行字典序排序
        String[] strs = new String[]{this.token, this.timestamp, this.nonce};
        Arrays.sort(strs);
        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0] + strs[1] + strs[2];
        // sha1加密
        String sha1Hex = DigestUtils.sha1Hex(str);
        // 比较签名值是否一致
        return sha1Hex.equals(this.signature);
    }

}
