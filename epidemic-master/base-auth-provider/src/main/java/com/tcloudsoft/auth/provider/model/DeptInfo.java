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
@TableName("t_dept_info")
public class DeptInfo extends Model<DeptInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 部门编号
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 状态(0正常 1禁用)
     */
    private Integer status;

    /**
     * 居住地编码(省)
     */
    private String province;

    /**
     * 居住地(市)
     */
    private String city;

    /**
     * 居住地(县)
     */
    private String district;

    /**
     * 居住地(乡镇/街道)
     */
    private String street;

    /**
     * 居住地（村/社区）
     */
    private String village;

    /**
     * 详细住址（门牌号、组）
     */
    private String origin;
}
