package com.tcloudsoft.web.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.web.provider.model.Isolation;
import com.tcloudsoft.web.provider.mapper.IsolationMapper;
import com.tcloudsoft.web.provider.service.IsolationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.web.provider.vo.IsolationVo;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuwei
 * @since 2021-12-06
 */
@Service
public class IsolationServiceImpl extends ServiceImpl<IsolationMapper, Isolation> implements IsolationService {

    @Resource
    IsolationMapper isolationMapper;

    @Override
    public IPage<IsolationVo> page(String name, String type, Integer status, String deptId, Integer current, Integer pageSize) {
        IPage<IsolationVo> page = new Page<>(current,pageSize);
        QueryWrapper<IsolationVo> query = Wrappers.query();
        if (TcloudUtils.isNotEmpty(name)){
            query.like("p.name",name);
        }
        if (TcloudUtils.isNotEmpty(type)){
            query.eq("v.type",type);
        }
        if (TcloudUtils.isNotEmpty(status)){
            if (0 == status){
                query.isNull("v.close_time");
            }else {
                query.isNotNull("v.close_time");
            }
        }
        if (TcloudUtils.isNotEmpty(deptId)){
            query.likeRight("v.dept_id",deptId);
        }
        query.eq("p.deleted",0);
        query.orderByDesc("v.creator_time");
        return isolationMapper.page(page,query);
    }


}
