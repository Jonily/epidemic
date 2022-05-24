package com.tcloudsoft.auth.provider.vo;

import com.tcloudsoft.auth.provider.enumtype.MenuType;
import com.tcloudsoft.utils.TcloudUtils;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo{
  private String id;
  /**
   * 父菜单ID
   */
  private String pid = "-1";

  /**
   * 显示名称
   */
  private String name;

  /**
   * 菜单图片
   */
  private String icon;

  /**
   * 菜单路径
   */
  private String href;

  /**
   * 类型
   */
  private MenuType type;

  /**
   * 权限标识
   */
  private String permissionKey;


  private String serviceId;

  private Integer orderFlag = 0;

  private List<MenuVo> children;

  public List<MenuVo> getChildren() {
    if (TcloudUtils.isEmpty(this.children)) {
      return null;
    }
    return this.children;
  }
}
