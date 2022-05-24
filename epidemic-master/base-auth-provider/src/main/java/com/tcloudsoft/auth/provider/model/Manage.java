package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-11-30
 */
@Data
@TableName("t_manage")
public class Manage extends Model<Manage> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 人员编号(工号)
     */
    private String code;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 部门信息ID
     */
    private String deptId;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 状态(0正常 1禁用)
     */
    private Integer status;

}
