package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tcloudsoft.auth.provider.enumtype.MenuType;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2020-05-14
 */
@Data
@TableName("t_menu")
public class Menu extends Model<Menu> {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.INPUT)
  private String id;

  /**
   * 父菜单ID
   */
  private String pid;

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


  /**
   * 服务ID
   */
  private String serviceId;

  /**
   * 排序
   */
  private Integer orderFlag = 0;

  /**
   * 是否删除
   */
  private Integer isDeleted;

  /**
   * 应用缩略图
   */
  private byte[] thumbnail;
}
