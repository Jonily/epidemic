package com.tcloudsoft.web.provider.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tcloudsoft.web.provider.model.Vaccination;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2021-12-06
 */
@Mapper
public interface VaccinationMapper extends BaseMapper<Vaccination> {

    @Select("select p.name as personName,v.* from t_vaccination v left join t_person p on v.id_card = p.id_card ${ew.customSqlSegment}")
    IPage<VaccinationVo> page(IPage<VaccinationVo> page, @Param(Constants.WRAPPER) Wrapper<VaccinationVo> queryWrapper);

    @Select("select p.name as personName,v.* from t_vaccination v left join t_person p on v.id_card = p.id_card where ${ew.sqlSegment}")
    List<VaccinationVo> list(@Param(Constants.WRAPPER) Wrapper<VaccinationVo> queryWrapper);

    // 已完成接种
    @Select("select IFNULL(count(*),0) from t_vaccination v inner join t_vaccines ve on (v.brand = ve.id and v.inoculation_num >= ve.number) where ${ew.sqlSegment}")
    Integer finishCount(@Param(Constants.WRAPPER) Wrapper<Vaccination> queryWrapper);

    // 未完成接种
    @Select("select IFNULL(count(*),0) from t_vaccination v inner join t_vaccines ve on (v.brand = ve.id and v.inoculation_num < ve.number) left join t_person p on v.id_card = p.id_card where ${ew.sqlSegment}")
    Integer noFinishQueryCount(@Param(Constants.WRAPPER) Wrapper<Vaccination> queryWrapper);

    @Select("select * from t_vaccination where id_card=#{idCard} and brand=#{brand} and deleted = 0")
    List<Vaccination> listByQuery(@Param("idCard")String idCard,@Param("brand")String brand);
}
