package com.tcloudsoft.utils.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserInfo {
  private String id;
  private String code;// 社区员工编码
  private String name;// 社区员工姓名
  private String password;// 密码
  private Integer status;
  private String deptId;
  private Long created;
}
