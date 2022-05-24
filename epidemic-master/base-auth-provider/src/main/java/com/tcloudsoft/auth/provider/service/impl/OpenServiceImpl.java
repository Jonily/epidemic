package com.tcloudsoft.auth.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.model.Open;
import com.tcloudsoft.auth.provider.mapper.OpenMapper;
import com.tcloudsoft.auth.provider.service.OpenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2021-12-21
 */
@Service
public class OpenServiceImpl extends ServiceImpl<OpenMapper, Open> implements OpenService {

    @Resource
    OpenMapper openMapper;
    @Override
    public Open getByOpenId(String openId) {
        QueryWrapper<Open> query = Wrappers.query();
        query.eq("open_id",openId);
        return this.getOne(query);
    }

    @Override
    public Open getOPenByOpenId(String openId) {
        return openMapper.getOpenByOpId(openId);
    }

    @Override
    public void saveOpen(Open open) {
        openMapper.insert(open);
    }


}
