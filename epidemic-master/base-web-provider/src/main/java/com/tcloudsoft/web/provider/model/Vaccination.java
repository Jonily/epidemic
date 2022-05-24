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
@TableName("t_vaccination")
public class Vaccination extends Model<Vaccination> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 最近一次接种时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;

    /**
     * 创建时间(第一次接种时间)
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatorTime;

    /**
     * 最近一次接种地点
     */
    private String area;

    /**
     * 已接种针数
     */
    private Integer inoculationNum;

    /**
     * 疫苗品种
     */
    private String brand;

    /**
     * 接种人身份证
     */
    private String idCard;

    /**
     * 0:未审核 1:已审核
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 社区部门
     */
    private String deptId;

    /**
     * 接种完成时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

}
