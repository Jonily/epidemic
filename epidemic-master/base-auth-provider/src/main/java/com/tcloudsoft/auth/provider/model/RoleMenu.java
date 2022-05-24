package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2020-06-02
 */
@Data
@TableName("t_role_menu")
public class RoleMenu extends Model<RoleMenu> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.UUID)
  private String id;

  private String roleId;

  private String menuId;
}
