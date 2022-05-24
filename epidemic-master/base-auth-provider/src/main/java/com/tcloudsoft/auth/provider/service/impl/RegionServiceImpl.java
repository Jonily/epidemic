package com.tcloudsoft.auth.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.model.Region;
import com.tcloudsoft.auth.provider.mapper.RegionMapper;
import com.tcloudsoft.auth.provider.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.utils.TcloudUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-11-30
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public String getCodeByName(String code) {
        QueryWrapper<Region> query = Wrappers.query();
        query.eq("id",code);
        Region region =  this.getOne(query);
        if (TcloudUtils.isEmpty(region)){
            return "";
        }
        return region.getName();
    }
}
