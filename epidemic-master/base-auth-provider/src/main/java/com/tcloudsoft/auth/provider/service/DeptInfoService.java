package com.tcloudsoft.auth.provider.service;

import com.tcloudsoft.auth.provider.model.DeptInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuwei
 * @since 2021-11-30
 */
public interface DeptInfoService extends IService<DeptInfo> {

    String getNameByCode(String code);

}
