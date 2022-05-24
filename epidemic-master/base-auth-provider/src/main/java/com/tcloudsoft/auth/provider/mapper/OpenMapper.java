package com.tcloudsoft.auth.provider.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tcloudsoft.auth.provider.model.Open;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloudsoft.auth.provider.model.Person;
import com.tcloudsoft.auth.provider.model.WechatUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2021-12-21
 */
public interface OpenMapper extends BaseMapper<Open> {
    @Select("select * from t_open where open_id = #{openId}")
    Open getOpenByOpId(@Param("openId") String openId);


}
