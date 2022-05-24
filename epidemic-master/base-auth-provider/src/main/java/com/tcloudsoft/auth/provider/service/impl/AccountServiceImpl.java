package com.tcloudsoft.auth.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Account;
import com.tcloudsoft.auth.provider.mapper.AccountMapper;
import com.tcloudsoft.auth.provider.model.User;
import com.tcloudsoft.auth.provider.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户信息表 服务实现类
 * </p>
 *
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public Account getByUid(String uid) {
        QueryWrapper<Account> query = Wrappers.query();
        query.eq("uid",uid);
        return this.getOne(query);
    }

    @Override
    public Account getByIdCard(String idCard) {
        QueryWrapper<Account> query = Wrappers.query();
        query.eq("user_name",idCard);
        return this.getOne(query);
    }

}
