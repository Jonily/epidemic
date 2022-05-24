package com.tcloudsoft.auth.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Dept;
import com.tcloudsoft.auth.provider.vo.DeptVo;
import com.tcloudsoft.auth.provider.vo.UserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuwei
 * @since 2021-07-09
 */
public interface DeptService extends IService<Dept> {

    List<DeptVo> getDeptTreeByUser(UserVo user);

    void saveDept(DeptVo deptVo) throws TcmsAuthException;

    void remove(List<String> ids) throws TcmsAuthException;

}