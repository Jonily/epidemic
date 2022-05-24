package com.tcloudsoft.web.provider.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.web.provider.model.Vaccination;
import com.tcloudsoft.web.provider.mapper.VaccinationMapper;
import com.tcloudsoft.web.provider.service.VaccinationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2021-12-06
 */
@Service
public class VaccinationServiceImpl extends ServiceImpl<VaccinationMapper, Vaccination> implements VaccinationService {

    @Resource
    VaccinationMapper vaccinationMapper;

    @Override
    public IPage<VaccinationVo> page(String idCard, String brand, Integer type,String deptId, Integer current, Integer pageSize) {
        IPage<VaccinationVo> page = new Page<>(current,pageSize);
        QueryWrapper<VaccinationVo> query = Wrappers.query();
        if (TcloudUtils.isNotEmpty(idCard)){
            query.eq("v.id_card",idCard);
        }
        if (TcloudUtils.isNotEmpty(brand)){
            query.eq("v.brand",brand);
        }
        if (TcloudUtils.isNotEmpty(type)){
            query.eq("v.status",type);
        }
        query.eq("v.deleted",0);
        if (TcloudUtils.isNotEmpty(deptId)){
            query.likeRight("v.dept_id",deptId);
        }
        query.orderByDesc("v.creator_time");
        return vaccinationMapper.page(page,query);
    }

    @Override
    public List<VaccinationVo> getList(String idCard, String brand, Integer type, String deptId) {
        QueryWrapper<VaccinationVo> query = Wrappers.query();
        if (TcloudUtils.isNotEmpty(idCard)){
            query.eq("v.id_card",idCard);
        }
        if (TcloudUtils.isNotEmpty(brand)){
            query.eq("v.brand",brand);
        }
        if (TcloudUtils.isNotEmpty(type)){
            query.eq("v.status",type);
        }
        if (TcloudUtils.isNotEmpty(deptId)){
            query.likeRight("v.dept_id",deptId);
        }
        query.orderByDesc("v.creator_time");
        return vaccinationMapper.list(query);
    }

    @Override
    public List<Vaccination> getByQuery(String idCard, String brand) {
        List<Vaccination> list = vaccinationMapper.listByQuery(idCard,brand);
        return list;
    }

    @Override
    public Integer count(String deptId,Integer type) {
        String nowDay = DateUtil.format(new Date(),"yyyy-MM-dd");
        QueryWrapper<Vaccination> query = Wrappers.query();
        Integer count = 0;
        switch (type){
            case 1:// 总次数
                query.likeRight("dept_id",deptId);
                query.eq("deleted",0);
                count = this.count(query);
                break;
            case 2:// 总完成次数
                query.likeRight("v.dept_id",deptId);
                query.eq("v.deleted",0);
                count = vaccinationMapper.finishCount(query);
                break;
            case 3:// 今日新增次数
                query.likeRight("dept_id",deptId);
                query.eq("deleted",0);
                query.ge("creator_time",nowDay+" 00:00:01");
                query.le("creator_time",nowDay+" 23:59:59");
                count = this.count(query);
                break;
            case 4:// 今日完成次数
                query.likeRight("v.dept_id",deptId);
                query.eq("v.deleted",0);
                query.ge("v.finish_time",nowDay+" 00:00:01");
                query.le("v.finish_time",nowDay+" 23:59:59");
                count = vaccinationMapper.finishCount(query);
        }
        if (TcloudUtils.isEmpty(count)){
            return 0;
        }
        return count;
    }

    @Override
    public Integer count(String deptId, Integer startAge, Integer endAge) {
        QueryWrapper<Vaccination> query = Wrappers.query();
        query.eq("v.dept_id",deptId);
        query.eq("v.deleted",0);// 正常
        if (TcloudUtils.isNotEmpty(startAge)){
            query.ge("p.age",startAge);
        }
        if (TcloudUtils.isNotEmpty(endAge)){
            query.lt("p.age",endAge);
        }
        query.eq("p.deleted",0);// 正常
        Integer count = vaccinationMapper.noFinishQueryCount(query);
        if (TcloudUtils.isEmpty(count)){
            return 0;
        }
        return count;
    }

    @Override
    public Integer countCode(String deptId, Integer codeStatus) {
        QueryWrapper<Vaccination> query = Wrappers.query();
        query.eq("v.dept_id",deptId);
        query.eq("v.deleted",0);// 正常
        if (TcloudUtils.isNotEmpty(codeStatus)){
            query.eq("p.code_status",codeStatus);
        }
        query.eq("p.deleted",0);// 正常
        Integer count = vaccinationMapper.noFinishQueryCount(query);
        if (TcloudUtils.isEmpty(count)){
            return 0;
        }
        return count;
    }
}
