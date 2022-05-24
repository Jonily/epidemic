package com.tcloudsoft.web.provider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.web.provider.model.Visit;
import com.tcloudsoft.web.provider.mapper.VisitMapper;
import com.tcloudsoft.web.provider.service.VisitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import com.tcloudsoft.web.provider.vo.VisitVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuwei
 * @since 2021-12-06
 */
@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {

    @Resource
    VisitMapper visitMapper;

    @Override
    public IPage<VisitVo> page(String idCard, Integer type, String startDate, String endDate, String deptId, Integer current, Integer pageSize) {
        IPage<VisitVo> page = new Page<>(current,pageSize);
        QueryWrapper<VisitVo> query = Wrappers.query();
        if (TcloudUtils.isNotEmpty(deptId)){
            query.likeRight("v.dept_id",deptId);
        }
        if (TcloudUtils.isNotEmpty(idCard)){
            query.eq("v.id_card",idCard);
        }
        if (TcloudUtils.isNotEmpty(type)){
            query.eq("v.type",type);
        }
        if (TcloudUtils.isNotEmpty(startDate)){
            String startTime = startDate + " 00:00:01";
            query.ge("v.creator_time",startTime);
        }
        if (TcloudUtils.isNotEmpty(endDate)){
            String endTime = endDate + " 23:59:59";
            query.le("v.creator_time",endTime);
        }
        query.eq("v.deleted",0);
        query.orderByDesc("v.creator_time");
        return visitMapper.page(page,query);
    }

    @Override
    public VisitVo queryById(String id) {
        QueryWrapper<VisitVo> query = Wrappers.query();
        if (TcloudUtils.isNotEmpty(id)){
            query.eq("id",id);
        }
        query.eq("deleted",0);
        return visitMapper.queryById(query);
    }

    @Override
    public Integer getVisitCount(String time,String deptId) {
        QueryWrapper<VisitVo> query = Wrappers.query();
        String start = time + " 00:00:00";
        String end = time + " 23:59:59";
        query.eq("type",0);// 来访
        if (TcloudUtils.isNotEmpty(start)){
            query.ge("creator_time",start);
        }
        if (TcloudUtils.isNotEmpty(end)){
            query.le("creator_time",end);
        }
        query.eq("deleted",0);
        query.likeRight("dept_id",deptId);
        return visitMapper.getVisitCount(query);
    }

    @Override
    public Integer getTripCount(String time,String deptId) {
        QueryWrapper<VisitVo> query = Wrappers.query();
        String start = time + " 00:00:00";
        String end = time + " 23:59:59";
        query.eq("type",1);
        if (TcloudUtils.isNotEmpty(start)){
            query.ge("creator_time",start);
        }
        if (TcloudUtils.isNotEmpty(end)){
            query.le("creator_time",end);
        }
        query.eq("deleted",0);
        query.likeRight("dept_id",deptId);
        return visitMapper.getTripCount(query);
    }

    @Override
    public Integer getVisitCountHistory(String deptId) {
        QueryWrapper<VisitVo> query = Wrappers.query();
        query.eq("type",0);
        query.eq("deleted",0);
        query.likeRight("dept_id",deptId);
        return visitMapper.getVisitCountHistory(query);
    }

    @Override
    public Integer getTripCountHistory(String deptId) {
        QueryWrapper<VisitVo> query = Wrappers.query();
        query.eq("type",1);
        query.eq("deleted",0);
        query.likeRight("dept_id",deptId);
        return visitMapper.getTripCountHistory(query);
    }

    @Override
    public List<VisitVo> getTodayVisitList(String time,String deptId) {
        QueryWrapper<VisitVo> query = Wrappers.query();
        String start = time + " 00:00:00";
        String end = time + " 23:59:59";
        query.eq("v.type",0);
        if (TcloudUtils.isNotEmpty(start)){
            query.ge("v.plan_time",start);
        }
        if (TcloudUtils.isNotEmpty(end)){
            query.le("v.plan_time",end);
        }
        query.eq("v.deleted",0);
        query.likeRight("v.dept_id",deptId);
        return visitMapper.getTodayVisitList(query);
    }

    @Override
    public List<VisitVo> getTodayTripList(String time,String deptId) {
        QueryWrapper<VisitVo> query = Wrappers.query();
        String start = time + " 00:00:00";
        String end = time + " 23:59:59";
        query.eq("v.type",1);
        if (TcloudUtils.isNotEmpty(start)){
            query.ge("v.plan_time",start);
        }
        if (TcloudUtils.isNotEmpty(end)){
            query.le("v.plan_time",end);
        }
        query.eq("v.deleted",0);
        query.likeRight("v.dept_id",deptId);
        return visitMapper.getTodayTripList(query);
    }
}
