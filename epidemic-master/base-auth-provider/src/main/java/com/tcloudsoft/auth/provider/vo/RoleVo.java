package com.tcloudsoft.auth.provider.vo;

import com.tcloudsoft.auth.provider.model.Role;
import lombok.Data;

@Data
public class RoleVo extends Role {

  /**
   * 创建人姓名
   */
  private String userName;

  /**
   * 创建人部门
   */
  private String deptName;

  /**
   * 权限字符串
   */
  private String privilege;

  /**
   * 是否可以删除
   */
  private int userNum;

}
