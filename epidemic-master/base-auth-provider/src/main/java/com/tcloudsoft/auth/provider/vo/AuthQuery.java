package com.tcloudsoft.auth.provider.vo;

import lombok.Getter;
import lombok.Setter;


/**
 * @Description 用户认证类
 */

@Setter
@Getter
public class AuthQuery {


  /**
   * @Description 用户名
   */

  private String username;


  /**
   * @Description 密码
   */

  private String password;


  /**
   * @Description 登录平台ID，管理员允许为空
   */
  private String appId;

}
