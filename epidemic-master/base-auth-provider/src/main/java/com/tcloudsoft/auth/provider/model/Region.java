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
@TableName("t_region")
public class Region extends Model<Region> {

    private static final long serialVersionUID = 1L;

    /**
     * 区划代码
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级区划代码
     */
    private Long parentId;

    private Integer agencyId;

    /**
     * 级别1-5,省市县镇村
     */
    private Integer type;
}
