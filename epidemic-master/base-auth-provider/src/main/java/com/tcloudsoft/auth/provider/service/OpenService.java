package com.tcloudsoft.auth.provider.service;

import com.tcloudsoft.auth.provider.model.Open;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2021-12-21
 */
public interface OpenService extends IService<Open> {

    Open getByOpenId(String openId);
    Open getOPenByOpenId(String openId);
    void saveOpen(Open open);

}
