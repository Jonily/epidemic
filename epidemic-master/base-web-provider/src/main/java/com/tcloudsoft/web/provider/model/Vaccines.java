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
@TableName("t_vaccines")
public class Vaccines extends Model<Vaccines> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 疫苗品牌名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date creatorTime;

    /**
     * 需接种针数
     */
    private Integer number;

}
