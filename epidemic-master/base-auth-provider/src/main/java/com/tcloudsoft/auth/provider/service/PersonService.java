package com.tcloudsoft.auth.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Person;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloudsoft.auth.provider.vo.PersonVo;

import java.util.List;

/**
 * <p>
 * 社区居民信息表 服务类
 * </p>
 *
 * @author liuwei
 * @since 2021-11-30
 */
public interface PersonService extends IService<Person> {

    IPage<Person> page (String name, String idCard, Integer codeStatus, String deptId, Integer current, Integer pageSize);

    List<Person> getListByQuery (String name, String idCard, String codeStatus, String deptId);

    void saveOrUpdate(PersonVo person, String deptId) throws IllegalArgumentException;

    // 通过身份证查询用户
    List<Person> findByPhone(String idCard,String id);

    default List<Person> findByPhone(String idCard){
        return findByPhone(idCard,null);
    }

}
