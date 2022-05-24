package com.tcloudsoft.auth.provider.service;

import com.tcloudsoft.auth.provider.model.Region;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuwei
 * @since 2021-11-30
 */
public interface RegionService extends IService<Region> {


    String getCodeByName(String code);

}
