package com.tcloudsoft.auth.provider.mapper;

import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloudsoft.auth.provider.model.WechatUser;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2020-11-10
 */
public interface WechatUserMapper extends BaseMapper<WechatUser> {
  @Select("select * from t_wechat_user where open_id=#{id}")
  WechatUser getWechatUserByOpId(@Param("id") String id);
}
