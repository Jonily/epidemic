package com.tcloudsoft.web.provider.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tcloudsoft.web.provider.model.Isolation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloudsoft.web.provider.model.IsolationExam;
import com.tcloudsoft.web.provider.vo.IsolationExamVo;
import com.tcloudsoft.web.provider.vo.IsolationVo;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2021-12-06
 */
public interface IsolationMapper extends BaseMapper<Isolation> {

    @Select("select p.name as personName,v.* from t_isolation v left join t_person p on v.id_card = p.id_card ${ew.customSqlSegment}")
    IPage<IsolationVo> page(IPage<IsolationVo> page, @Param(Constants.WRAPPER) Wrapper<IsolationVo> queryWrapper);

    @Select("select name,phone,id_card as idCard from t_person where id_card = #{idCard}")
    IsolationExamVo getPerson(@Param("idCard")String idCard);

}
