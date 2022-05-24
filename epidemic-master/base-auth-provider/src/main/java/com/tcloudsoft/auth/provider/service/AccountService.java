package com.tcloudsoft.auth.provider.service;

import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 账户信息表 服务类
 * </p>
 *
 */
public interface AccountService extends IService<Account> {

    Account getByUid(String uid);

    Account getByIdCard(String idCard);

}
