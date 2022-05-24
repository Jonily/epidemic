package com.tcloudsoft.auth.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloudsoft.auth.provider.model.WechatUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author longgd
 * @since 2020-11-10
 */
public interface WechatUserService extends IService<WechatUser> {
  WechatUser getWechatUserByOpId(String id);
}
