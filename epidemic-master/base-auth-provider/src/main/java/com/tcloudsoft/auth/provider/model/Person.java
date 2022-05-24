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
 * 社区居民信息表
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-11-30
 */
@Data
@TableName("t_person")
public class Person extends Model<Person> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 居住地编码(省)
     */
    private String province;

    /**
     * 居住地(市)
     */
    private String city;

    /**
     * 居住地(县)
     */
    private String district;

    /**
     * 居住地(乡镇/街道)
     */
    private String street;

    /**
     * 居住地（村/社区）
     */
    private String village;

    /**
     * 详细住址（门牌号、组）
     */
    private String origin;

    /**
     * 用户创建时间
     */
    private Date creatorTime;

    /**
     * 当前最新健康码状态(1:绿色 2:黄色 3:红色)
     */
    private Integer codeStatus;

    /**
     * 最近一次健康码状态
     */
    private Integer lastCodeStatus;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 部门ID
     */
    private String deptId;

}
