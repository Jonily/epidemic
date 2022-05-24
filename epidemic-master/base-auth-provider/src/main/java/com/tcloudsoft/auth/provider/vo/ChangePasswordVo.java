package com.tcloudsoft.auth.provider.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordVo {
  private String currentPassword;
  private String password;
  private String confirmPassword;
}
