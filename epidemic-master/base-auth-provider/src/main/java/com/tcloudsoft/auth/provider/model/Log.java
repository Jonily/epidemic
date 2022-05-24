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
 * @author zhuolin.Huang
 * @since 2021-09-08
 */
@Data
@TableName("t_log")
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 操作人
     */
    private String username;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String method;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String args;

    /**
     * 访问的类
     */
    private String className;

    /**
     * 请求是否成功
     */
    private Integer success;

    /**
     * 请求时间
     */
    private Date creatorTime;

    /**
     * 请求耗时(毫秒)
     */
    private Long requestTime;

    /**
     * 备注
     */
    private String description;
}
