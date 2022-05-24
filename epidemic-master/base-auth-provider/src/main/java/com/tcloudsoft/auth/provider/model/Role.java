package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2020-05-14
 */
@TableName("t_role")
@Setter
@Getter
public class Role extends Model<Role> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.UUID)
  private String id;

  /**
   * 角色名称
   */
  private String name;

  /**
   * 角色描述
   */
  private String description;


  /**
   * 创建人
   */
  private String userId;


  /**
   * 创建人姓名
   */
  @TableField(exist = false)
  private String userName;

  /**
   * 创建时间
   */
  @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date creatorTime;

  /**
   * 是否删除
   */
  private Integer isDeleted;

  /**
   * 权限字符串
   */
  @TableField(exist = false)
  private String privilege;

  @TableField(exist = false)
  private int userNum;

  @TableField(exist = false)
  private String deptName;

  @Override
  protected Serializable pkVal() {
    return this.id;
  }

  @Override
  public String toString() {
    return "Role{" + "id=" + id + ", name=" + name + ", description=" + description + "}";
  }
}
