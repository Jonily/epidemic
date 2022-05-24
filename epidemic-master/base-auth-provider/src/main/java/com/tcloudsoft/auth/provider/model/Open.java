package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2021-12-21
 */
@Data
@TableName("t_open")
public class Open extends Model<Open> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * openId
     */
    private String openId;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 更新时间
     */
    private Date updateTime;

}
