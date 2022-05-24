package com.tcloudsoft.web.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.web.provider.mapper.IsolationMapper;
import com.tcloudsoft.web.provider.model.Isolation;
import com.tcloudsoft.web.provider.model.IsolationExam;
import com.tcloudsoft.web.provider.mapper.IsolationExamMapper;
import com.tcloudsoft.web.provider.service.IsolationExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class IsolationExamServiceImpl extends ServiceImpl<IsolationExamMapper, IsolationExam> implements IsolationExamService {

    @Resource
    IsolationExamMapper isolationExamMapper;
    @Override
    public List<IsolationExam> getList(String isolationId, Integer status) {
        QueryWrapper<IsolationExam> query = Wrappers.query();
        query.eq("1","1");
        if (TcloudUtils.isNotEmpty(isolationId)){
            query.eq("isolation_id",isolationId);
        }
        if (TcloudUtils.isNotEmpty(status)){
            query.eq("status",status);
        }
        query.orderByDesc("creator_time");
        return this.list(query);
    }

    @Override
    public Isolation getByIdCard(String idCard) {

        return isolationExamMapper.getIsolation(idCard);
    }
}
