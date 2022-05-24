package com.tcloudsoft.auth.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.model.DeptInfo;
import com.tcloudsoft.auth.provider.mapper.DeptInfoMapper;
import com.tcloudsoft.auth.provider.service.DeptInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.utils.TcloudUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-11-30
 */
@Service
public class DeptInfoServiceImpl extends ServiceImpl<DeptInfoMapper, DeptInfo> implements DeptInfoService {

    @Override
    public String getNameByCode(String code) {
        QueryWrapper<DeptInfo> query = Wrappers.query();
        query.eq("id",code);
        query.eq("status",0);
        DeptInfo deptInfo = this.getOne(query);
        if (TcloudUtils.isEmpty(deptInfo)){
            return "";
        }
        return deptInfo.getDeptName();
    }
}
