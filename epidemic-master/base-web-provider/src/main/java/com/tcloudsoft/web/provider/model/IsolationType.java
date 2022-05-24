package com.tcloudsoft.web.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2021-12-06
 */
@Data
@TableName("t_isolation_type")
public class IsolationType extends Model<IsolationType> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 阶段名称
     */
    private String name;

    /**
     * 需隔离天数
     */
    private Integer dayNum;

    /**
     * 排序
     */
    private Integer sort;

}
