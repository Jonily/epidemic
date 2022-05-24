package com.tcloudsoft.web.provider.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tcloudsoft.web.provider.model.Visit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import com.tcloudsoft.web.provider.vo.VisitVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuwei
 * @since 2021-12-06
 */
public interface VisitService extends IService<Visit> {

    IPage<VisitVo> page(String idCard, Integer type, String startDate, String endDate,String deptId, Integer current, Integer pageSize);

    VisitVo queryById(String id);

    Integer getVisitCount(String time,String deptId);

    Integer getTripCount(String time,String deptId);

    Integer getVisitCountHistory(String deptId);

    Integer getTripCountHistory(String deptId);

    List<VisitVo> getTodayVisitList(String time,String deptId);

    List<VisitVo> getTodayTripList(String time,String deptId);
}
