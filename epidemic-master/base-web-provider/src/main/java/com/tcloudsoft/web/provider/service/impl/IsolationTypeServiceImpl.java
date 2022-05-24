package com.tcloudsoft.web.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.web.provider.model.IsolationType;
import com.tcloudsoft.web.provider.mapper.IsolationTypeMapper;
import com.tcloudsoft.web.provider.service.IsolationTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2021-12-06
 */
@Service
public class IsolationTypeServiceImpl extends ServiceImpl<IsolationTypeMapper, IsolationType> implements IsolationTypeService {

    @Override
    public IsolationType get(Integer sort) {
        QueryWrapper<IsolationType> query = Wrappers.query();
        query.eq("1","1");
        if (TcloudUtils.isNotEmpty(sort)){
            query.eq("sort",sort);
        }
        return this.getOne(query);
    }
}
