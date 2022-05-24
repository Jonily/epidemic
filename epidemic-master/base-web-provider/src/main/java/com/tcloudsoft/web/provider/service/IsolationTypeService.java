package com.tcloudsoft.web.provider.service;

import com.tcloudsoft.web.provider.model.IsolationType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuwei
 * @since 2021-12-06
 */
public interface IsolationTypeService extends IService<IsolationType> {

    IsolationType get(Integer sort);

}
