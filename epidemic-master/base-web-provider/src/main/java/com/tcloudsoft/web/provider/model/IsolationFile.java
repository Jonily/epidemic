package com.tcloudsoft.web.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_isolation_file")
public class IsolationFile extends Model<IsolationFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件下载地址
     */
    private String url;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 0:未审核 1:已审核
     */
    private Integer status;

}
