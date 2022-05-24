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
 * 账户信息表
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-11-30
 */
@Data
@TableName("t_account")
public class Account extends Model<Account> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 账户
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 上一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 居民信息外键
     */
    private String uid;

    /**
     * 最近一次修改账户信息时间
     */
    private Date updateTime;

}
