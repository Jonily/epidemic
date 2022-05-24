package com.tcloudsoft.auth.provider.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tcloudsoft.auth.provider.model.Person;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 * 社区居民信息表 Mapper 接口
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-11-30
 */
@Mapper
public interface PersonMapper extends BaseMapper<Person> {

    @Select("select * from t_person ${ew.customSqlSegment}")
    IPage<Person> page(IPage<Person> page, @Param(Constants.WRAPPER) Wrapper<Person> queryWrapper);

}
