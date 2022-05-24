package com.tcloudsoft.auth.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.mapper.DeptMapper;
import com.tcloudsoft.auth.provider.model.Dept;
import com.tcloudsoft.auth.provider.service.DeptService;
import com.tcloudsoft.auth.provider.vo.DeptVo;
import com.tcloudsoft.auth.provider.vo.UserVo;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-07-09
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    DeptMapper deptMapper;

    @Override
    public List<DeptVo> getDeptTreeByUser(UserVo user) {
        String pid = "-1";
        // 如果部门ID不为空，则看自己部门以及下属部门
        if (TcloudUtils.isNotEmpty(user.getDeptId())) {
            String id = user.getDeptId();
            Dept d = this.getById(id);
            if (d != null) {
                List<DeptVo> deptVos = new ArrayList<>();
                List<DeptVo> children = this.getDeptByPid(d.getId());
                DeptVo vo = new DeptVo();
                BeanUtils.copyProperties(d, vo);
                vo.setChildren(children);
                deptVos.add(vo);
                return deptVos;
            }
        }
        List<DeptVo> deptVos = this.getDeptByPid(pid);
        return deptVos;
    }

    @Override
    public void saveDept(DeptVo deptVo) throws TcmsAuthException {
        if (TcloudUtils.isEmpty(deptVo.getId())) {
            // 新增部门信息
            String pid = deptVo.getPid();
            // 有上级部门
            if (TcloudUtils.isNotEmpty(pid) && !"-1".equals(pid)) {
                Dept parentDetp = this.getById(deptVo.getPid());
                String linkId = this.getDeptLinkId(parentDetp.getId());
                deptVo.setId(linkId);
            } else {
                // 无上级部门
                String linkId = this.getDeptLinkId("");
                deptVo.setId(linkId);
            }
            // 新增部门
            Dept dept = new Dept();
            BeanUtils.copyProperties(deptVo, dept);
            if (TcloudUtils.isEmpty(dept.getCode())) {
                dept.setCode(dept.getId());
            }
            if (TcloudUtils.isEmpty(dept.getCreatorTime())) {
                dept.setCreatorTime(new Date());
            }
            this.save(dept);
        } else {
            // 更新部门信息
            Dept dept = new Dept();
            BeanUtils.copyProperties(deptVo, dept);
            this.updateById(dept);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(List<String> ids) throws TcmsAuthException {
        for (String id : ids) {
            this.removeById(id);
        }
    }

    public String getDeptLinkId(String parentLinkId) throws TcmsAuthException {
        String maxDeptId = "";
        long deptidvar = 0;
        String linkId = deptMapper.getMaxLinkId(parentLinkId + "%", parentLinkId.length() + 4);
        if (linkId != null && !"".equals(linkId)) {
            maxDeptId = String.valueOf(linkId);
        } else {
            maxDeptId = parentLinkId;
        }
        if (maxDeptId.equals(parentLinkId)) {
            maxDeptId = parentLinkId + "0001";
        } else {
            deptidvar = Long.valueOf(maxDeptId).longValue();
            if (maxDeptId.compareTo(parentLinkId + "9999") < 0) {
                maxDeptId = String.valueOf(deptidvar + 1);
            } else {
                throw new TcmsAuthException(ResponseCodeEnum.C00014);
            }
        }
        return maxDeptId;
    }

    private List<DeptVo> getDeptByPid(String pid) {
        QueryWrapper<Dept> query = Wrappers.query();
        query.eq("pid", pid);
        List<Dept> lists = this.list(query);
        List<DeptVo> result = new ArrayList<>();
        for (Dept dept : lists) {
            DeptVo vo = new DeptVo();
            BeanUtils.copyProperties(dept, vo);
            result.add(vo);
        }
        for (DeptVo vo : result) {
            List<DeptVo> vos = this.getDeptByPid(vo.getId());
            vo.setChildren(vos);
        }
        return result;
    }
}
