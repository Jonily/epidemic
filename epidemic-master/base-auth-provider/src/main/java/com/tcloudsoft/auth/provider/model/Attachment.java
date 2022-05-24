package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-03-03
 */
@Data
@TableName("t_attachment")
public class Attachment extends Model<Attachment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件服务器路径
     */
    private String path;

    private Date createTime;

    private String url;

    private String refId;
}
