package com.tcloudsoft.auth.provider.service;

import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Account;
import com.tcloudsoft.auth.provider.model.Manage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloudsoft.auth.provider.vo.AuthQuery;
import com.tcloudsoft.auth.provider.vo.AuthResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2021-11-30
 */
public interface ManageService extends IService<Manage> {

    // 根据token获取用户信息
    AuthResult findByToken(String token) throws TcmsAuthException;

    AuthResult auth(AuthQuery query) throws IllegalArgumentException;

    Manage findByUserName(String userName) throws IllegalArgumentException;

    void logout(String token) throws TcmsAuthException;

    void check(String token) throws TcmsAuthException;

}
