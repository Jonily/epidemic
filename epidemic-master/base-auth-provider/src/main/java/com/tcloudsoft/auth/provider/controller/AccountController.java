package com.tcloudsoft.auth.provider.controller;


import com.tcloudsoft.auth.provider.mapper.OpenMapper;
import com.tcloudsoft.auth.provider.model.Account;
import com.tcloudsoft.auth.provider.model.DeptInfo;
import com.tcloudsoft.auth.provider.model.Open;
import com.tcloudsoft.auth.provider.model.Person;
import com.tcloudsoft.auth.provider.service.AccountService;
import com.tcloudsoft.auth.provider.service.DeptInfoService;
import com.tcloudsoft.auth.provider.service.OpenService;
import com.tcloudsoft.auth.provider.service.PersonService;
import com.tcloudsoft.auth.provider.utils.QueryUtils;
import com.tcloudsoft.auth.provider.vo.PersonVo;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.utils.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;


/**
 * @description: 账户信息表 前端控制器
 * @author zhuolin.Huang
 * @date 2022/5/24 19:18
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    AccountService accountService;
    @Resource
    PersonService personService;
    @Value("${auth.password}")
    String password;
    @Resource
    OpenService openService;
    @Resource
    DeptInfoService deptInfoService;

    @Resource
    OpenMapper openMapper;

    @ApiOperation(value = "微信端登录",notes = "微信端登录")
    @PostMapping("/login")
    public ResponseData<Object> weChatLogin(@RequestHeader(WebConstants.OPENID)String openId, @RequestBody PersonVo vo){
        try {
            if (TcloudUtils.isEmpty(vo.getIdCard())){
                return ResponseData.fail("身份证不能为空！");
            }
            if (TcloudUtils.isEmpty(vo.getAge()) || vo.getAge() <= 0){
                return ResponseData.fail("请输入正确年龄！");
            }
            if (TcloudUtils.isEmpty(vo.getPhone()) || vo.getPhone().length() != 11){
                return ResponseData.fail("请输入正确的手机号！");
            }
            boolean variety = QueryUtils.idCardVerification(vo.getIdCard());
            if (!variety){
                return ResponseData.fail("身份证不合法！请检查后再重试");
            }
            Person target = new Person();// 返回对象
            Account account = accountService.getByIdCard(vo.getIdCard());
            if (TcloudUtils.isEmpty(account)){
                if (TcloudUtils.isEmpty(vo.getDeptId())){
                    return ResponseData.fail("未查找到您的信息，请填写所在社区再进行登录");
                }
                DeptInfo deptInfo = deptInfoService.getById(vo.getDeptId());
                if (TcloudUtils.isEmpty(deptInfo)){
                    return ResponseData.fail("社区不存在！");
                }
                Person p = new Person();
                BeanUtils.copyProperties(vo,p);
                p.setCreatorTime(new Date());
                p.setProvince(deptInfo.getProvince());
                p.setCity(deptInfo.getCity());
                p.setDistrict(deptInfo.getDistrict());
                p.setStreet(deptInfo.getStreet());
                p.setVillage(deptInfo.getVillage());
                p.setDeptId(vo.getDeptId());
                p.setDeleted(0);
                target = p;
                personService.save(p);

                //添加账号
                Account acc = new Account();
                acc.setUid(vo.getId());
                acc.setUserName(p.getIdCard());
                acc.setPassword(password);// 默认密码 888888
                acc.setUid(p.getId());
                accountService.save(acc);
            }else {
                // 更新用户信息
                Person old = personService.getById(account.getUid());
                if (TcloudUtils.isNotEmpty(vo.getName())) old.setName(vo.getName());
                if (TcloudUtils.isNotEmpty(vo.getPhone()))old.setPhone(vo.getPhone());
                if (TcloudUtils.isEmpty(vo.getAge()))old.setAge(vo.getAge());
                if (TcloudUtils.isNotEmpty(vo.getCodeStatus()))old.setCodeStatus(vo.getCodeStatus());
                DeptInfo deptInfo = deptInfoService.getById(vo.getDeptId());
                if (TcloudUtils.isEmpty(deptInfo)){
                    return ResponseData.fail("社区不存在！");
                }
                old.setProvince(deptInfo.getProvince());
                old.setCity(deptInfo.getCity());
                old.setDistrict(deptInfo.getDistrict());
                old.setStreet(deptInfo.getStreet());
                old.setVillage(deptInfo.getVillage());
                old.setDeptId(vo.getDeptId());
                old.setOrigin(vo.getOrigin());
//                BeanUtils.copyProperties(vo,old);
                old.setLastCodeStatus(old.getCodeStatus());// 更新健康码
                target = old;
                personService.updateById(old);// 修改信息
            }
            PersonVo person = new PersonVo();
            BeanUtils.copyProperties(target,person);
            DeptInfo deptInfo = deptInfoService.getById(vo.getDeptId());
            person.setDeptName(deptInfo.getDeptName());
            switch (target.getCodeStatus()){
                case 1:person.setCodeName("绿码");break;
                case 2:person.setCodeName("黄码");break;
                case 3:person.setCodeName("红码");break;
            }
            // 判断该openID是否已认证
            Open open = openService.getByOpenId(openId);
            if (TcloudUtils.isEmpty(open)){
                Open op = new Open();
                op.setId(person.getId());
                op.setIdCard(vo.getIdCard());
                op.setOpenId(openId);
                op.setUpdateTime(new Date());
                openService.save(op);
            }else {
                open.setIdCard(vo.getIdCard());
                open.setUpdateTime(new Date());
                openService.updateById(open);
            }
            return ResponseData.ok(person);
        }catch (Exception e){
            log.error("登录失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

}
