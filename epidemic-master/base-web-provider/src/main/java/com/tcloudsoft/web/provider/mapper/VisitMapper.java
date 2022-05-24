package com.tcloudsoft.web.provider.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tcloudsoft.web.provider.model.Visit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloudsoft.web.provider.vo.VisitVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuwei
 * @since 2021-12-06
 */
public interface VisitMapper extends BaseMapper<Visit> {
    @Select("select p.name as visitPersonName,v.* from t_visit v left join t_person p on (v.id_card = p.id_card and p.deleted = 0) ${ew.customSqlSegment}")
    IPage<VisitVo> page(IPage<VisitVo> page,@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("SELECT * from t_visit WHERE ${ew.sqlSegment}")
    VisitVo queryById(@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("select p.name as visitPersonName,p.phone as visitPhone,p.code_status as codeStatus,v.* from t_visit v left join t_person p on (v.id_card = p.id_card and p.deleted = 0) where v.id = #{id} ")
    VisitVo getByQuery(@Param("id")String id);

    @Select("select IFNULL(count(*),0) AS visitCount from t_visit where ${ew.sqlSegment}")
    Integer getVisitCount(@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("select IFNULL(count(*),0) AS tripCount from t_visit  where ${ew.sqlSegment}")
    Integer getTripCount(@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("select IFNULL(count(*),0) AS visitHistoryCount from t_visit where ${ew.sqlSegment}")
    Integer getVisitCountHistory(@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("select IFNULL(count(*),0) AS tripHistoryCount from t_visit where ${ew.sqlSegment}")
    Integer getTripCountHistory(@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("select p.name as visitPersonName,v.* from t_visit v left join t_person p on (v.id_card = p.id_card and p.deleted = 0) where ${ew.sqlSegment}")
    List<VisitVo> getTodayVisitList(@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("select p.name as visitPersonName,v.* from t_visit v left join t_person p on (v.id_card = p.id_card and p.deleted = 0) where ${ew.sqlSegment}")
    List<VisitVo> getTodayTripList(@Param(Constants.WRAPPER) QueryWrapper<VisitVo> query);

    @Select("select name from t_region where id = #{code}")
    String selectRegionName(@Param("code")String code);

    @Select("select id_card as idCard,IFNULL(count(*),0) as count from t_visit where dept_id = #{deptId} and deleted = 0  group by id_card order by count(*) desc")
    List<Map<String,Object>> selectRankIng(@Param("deptId")String deptId);

    @Select("select name from t_person where id_card = #{idCard} and deleted = 0 ")
    String selectPersonName(@Param("idCard")String idCard);
}
