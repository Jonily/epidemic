package com.tcloudsoft.auth.provider.vo;

import com.tcloudsoft.auth.provider.model.Role;
import com.tcloudsoft.auth.provider.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserVo extends User {

  /**
   * 角色名称
   */
  private String roleName;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 角色
   */
  private RoleVo role;

  private List<Role> roles;

  /**
   * 角色ID
   */
  private List<String> roleIds;
  /**
   * 角色名称
   */
  private List<String> roleNames;

  /**
   * 菜单数组
   */
  private List<MenuVo> menus = new ArrayList<>();
}
