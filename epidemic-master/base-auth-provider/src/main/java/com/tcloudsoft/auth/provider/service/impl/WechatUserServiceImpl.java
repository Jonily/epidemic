package com.tcloudsoft.auth.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.auth.provider.mapper.WechatUserMapper;
import com.tcloudsoft.auth.provider.model.WechatUser;
import com.tcloudsoft.auth.provider.service.WechatUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author longgd
 * @since 2020-11-10
 */
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser>
    implements WechatUserService {
  @Autowired
  WechatUserMapper wechatUserMapper;

  @Override
  public WechatUser getWechatUserByOpId(String id) {
    return wechatUserMapper.getWechatUserByOpId(id);
  }

}
