package com.tcloudsoft.auth.provider.vo;

import lombok.Data;

@Data
public class AuthVo {

  private String account;// 登录账号 手机号或者身份证
  private String password;// 登录密码
  private String phone;// 手机号
  private String verifyCode;// 手机验证码
  private String smsType;// 短信类型
  private Boolean checkPwd;// 是否验证密码，短息登录不验证密码


}
