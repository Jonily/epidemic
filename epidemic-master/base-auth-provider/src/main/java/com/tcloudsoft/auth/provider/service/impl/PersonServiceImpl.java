package com.tcloudsoft.auth.provider.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Account;
import com.tcloudsoft.auth.provider.model.Person;
import com.tcloudsoft.auth.provider.mapper.PersonMapper;
import com.tcloudsoft.auth.provider.service.AccountService;
import com.tcloudsoft.auth.provider.service.PersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.auth.provider.vo.PersonVo;
import com.tcloudsoft.utils.TcloudUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 社区居民信息表 服务实现类
 * </p>
 *
 * @since 2021-11-30
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {

    @Resource
    PersonMapper personMapper;
    @Resource
    AccountService accountService;
    @Value("${auth.password}")
    String password;

    @Override
    public IPage<Person> page(String name, String idCard, Integer codeStatus, String deptId, Integer current, Integer pageSize) {
        IPage<Person> page = new Page<>(current,pageSize);
        QueryWrapper<Person> query = Wrappers.query();
        if (TcloudUtils.isNotEmpty(name)){
            query.like("name",name);
        }
        if (TcloudUtils.isNotEmpty(idCard)){
            query.eq("id_card",idCard);
        }
        if (TcloudUtils.isNotEmpty(deptId)){
            query.likeRight("dept_id",deptId);
        }
        if (TcloudUtils.isNotEmpty(codeStatus)){
            query.eq("code_status",codeStatus);
        }
        query.eq("deleted",0);
        query.orderByDesc("creator_time");
        return personMapper.page(page,query);
    }

    @Override
    public List<Person> getListByQuery(String name, String idCard, String codeStatus, String deptId) {
        QueryWrapper<Person> query = Wrappers.query();
        if (TcloudUtils.isNotEmpty(name)){
            query.like("name",name);
        }
        if (TcloudUtils.isNotEmpty(idCard)){
            query.eq("id_card",idCard);
        }
        if (TcloudUtils.isNotEmpty(deptId)){
            query.likeRight("dept_id",deptId);
        }
        if (TcloudUtils.isNotEmpty(codeStatus)){
            query.eq("code_status",codeStatus);
        }
        query.eq("deleted",0);
        query.orderByDesc("creator_time");
        return this.list(query);
    }

    @Transactional(rollbackFor = Exception.class)// 遇到异常则数据库进行回滚
    @Override
    public void saveOrUpdate(PersonVo vo, String deptId) throws IllegalArgumentException {
        if (TcloudUtils.isEmpty(vo.getPhone())) throw new IllegalArgumentException("手机号码为必填项*！");
        Person person = new Person();
        BeanUtils.copyProperties(vo,person);
        if (TcloudUtils.isEmpty(person.getId())){
            List<Person> variety = this.findByPhone(person.getIdCard());
            if (TcloudUtils.isNotEmpty(variety) && variety.size() > 0) throw new IllegalArgumentException("该用户已存在！");
            person.setCreatorTime(new Date());
            person.setDeleted(0);
        }else {
            List<Person> variety = this.findByPhone(person.getIdCard(),person.getId());
            if (TcloudUtils.isNotEmpty(variety) && variety.size() > 0)
                throw new IllegalArgumentException("该用户已存在！");
        }
        person.setDeptId(deptId);// 跟随社区管理员部门
        this.saveOrUpdate(person);

        // 为新增的用户添加账户
        if (TcloudUtils.isEmpty(vo.getId())){
            Account account = new Account();
            account.setUid(person.getId());
            account.setUserName(person.getIdCard());
            account.setPassword(password);// 默认密码 888888
            accountService.save(account);
        }else {
            Account account = accountService.getByUid(person.getId());
            if (TcloudUtils.isNotEmpty(account)){
                account.setUserName(person.getIdCard());// 更新账户
                account.setUpdateTime(new Date());
                accountService.updateById(account);
            }
        }
    }

    @Override
    public List<Person> findByPhone(String idCard, String id) {
        QueryWrapper<Person> query = Wrappers.query();
        query.eq("id_card",idCard);
        query.eq("deleted",0);
        if (TcloudUtils.isNotEmpty(id)){
            query.ne("id",id);
        }
        List<Person> list = this.list(query);
        return this.list(query);
    }
}
