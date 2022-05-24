package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2020-05-14
 */
@TableName("t_user")
@Setter
@Getter
public class User extends Model<User> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.UUID)
  private String id;

  /**
   * 用户名
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 姓名
   */
  private String name;

  /**
   * 角色ID
   */
  private String roleId;


  /**
   * 是否锁定
   */
  private Boolean isLock;

  /**
   * 上传登录时间
   */
  @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastLoginTime;

  /**
   * 是否管理员
   */
  private Boolean isAdmin;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 手机号码
   */
  private String phone;

  /**
   * 部门ID
   */
  private String deptId;

  /**
   * 是否删除
   */
  private Integer isDeleted;

}
