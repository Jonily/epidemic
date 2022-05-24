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
 * @author liuwei
 * @since 2021-12-06
 */
@Data
@TableName("t_visit")
public class Visit extends Model<Visit> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 来访人员身份证
     */
    private String idCard;

    /**
     * 来源省
     */
    private String fromProvince;

    /**
     * 来源市
     */
    private String fromCity;

    /**
     * 来源县
     */
    private String fromDistrict;

    /**
     * 乡镇/街道
     */
    private String fromStreet;

    /**
     * 村/居委会
     */
    private String fromVillage;

    /**
     * 来源方式ID
     */
    private String fromModel;

    /**
     * 目标省
     */
    private String targetProvince;

    /**
     * 目标市
     */
    private String targetCity;

    /**
     * 目标区县
     */
    private String targetDistrict;

    /**
     * 目标区县/乡镇
     */
    private String targetStreet;

    /**
     * 目标村/居委会
     */
    private String targetVillage;

    /**
     * 滞留天数
     */
    private Integer dayNum;

    /**
     * 原因
     */
    private String reason;

    /**
     * 创建时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatorTime;

    /**
     * 动身时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date planTime;

    /**
     * 0：来访 1:离开
     */
    private Integer type;

    /**
     * 是否删除
     */
    private Integer deleted;

    private String deptId;

}
