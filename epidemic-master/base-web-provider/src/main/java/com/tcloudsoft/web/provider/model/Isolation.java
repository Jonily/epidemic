package com.tcloudsoft.web.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2021-12-06
 */
@Data
@TableName("t_isolation")
public class Isolation extends Model<Isolation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;




    /**
     * 创建时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatorTime;

    /**
     * 隔离人员
     */
    private String idCard;

    /**
     * 开始隔离时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;

    /**
     * 隔离类型 1:集中隔离 2:医院留观 3:社区管理 4:解除隔离
     */
    private String type;

    /**
     * 详细地点
     */
    private String origin;

    /**
     * 备注/描述
     */
    private String describtion;

    /**
     * 本阶段预计解除隔离时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planCloseTime;

    /**
     * 本阶段实际解除隔离时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 部门id
     */
    private String deptId;
}
