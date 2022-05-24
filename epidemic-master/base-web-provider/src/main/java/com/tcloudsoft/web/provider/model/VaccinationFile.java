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
@TableName("t_vaccination_file")
public class VaccinationFile extends Model<VaccinationFile> {

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
     * 接种信息ID
     */
    private String targetId;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 最近一次下载时间
     */
    private Date lastDownloadTime;

    /**
     * 下载次数
     */
    private Integer downloadNum;

}
