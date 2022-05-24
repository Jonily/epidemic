package com.tcloudsoft.web.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 */
@Data
@TableName("t_isolation_exam")
public class IsolationExam extends Model<IsolationExam> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 隔离信息ID
     */
    private String isolationId;

    /**
     * 原因描述
     */
    private String reason;

    /**
     * 创建时间
     */
    @JsonFormat(locale = "liu", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatorTime;

    /**
     * 状态: 0:审核中  1:已经解除
     */
    private Integer status;

}
