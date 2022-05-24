package com.tcloudsoft.auth.provider.vo;

import com.tcloudsoft.utils.common.UserVo;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;

@Setter
@Getter
public class AuthResult {
  private String token;
  private Date expireAt;
  private UserVo user;
  private String appId;
}
