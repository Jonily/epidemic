package com.tcloudsoft.auth.provider.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * <p>
 * 知识产权注册表
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-07-05
 */
@Data
@TableName("t_register")
public class Register extends Model<Register> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 手机授权码
     */
    private String openId;

    /**
     * 别名
     */
    private String nickName;

    /**
     * 警号/身份证号
     */
    private String number;

    /**
     * 类别 警员/违章人员
     */
    private String type;

}
