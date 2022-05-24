package com.tcloudsoft.auth.provider.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.enumtype.PersonEnum;
import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.model.Account;
import com.tcloudsoft.auth.provider.model.Person;
import com.tcloudsoft.auth.provider.service.*;
import com.tcloudsoft.auth.provider.utils.QueryUtils;
import com.tcloudsoft.auth.provider.vo.AuthResult;
import com.tcloudsoft.auth.provider.vo.PersonVo;
import com.tcloudsoft.utils.Excel.ExcelModel;
import com.tcloudsoft.utils.Excel.GrainExcelUtils;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.common.PageVo;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户信息
 * @author zhuolin.Huang
 * @date 2022/5/24 19:21
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    @Resource
    PersonService personService;
    @Resource
    ManageService manageService ;
    @Value("${auth.password}")
    String password;
    @Resource
    AccountService accountService;
    @Resource
    RegionService regionService;
    @Resource
    DeptInfoService deptInfoService;

    @ApiOperation(value = "分页查询用户列表",notes = "web端社区用户管理")
    @GetMapping("/page")
    public ResponseData<Object> page(
            @RequestParam(required = false) String idCard, @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer codeStatus, @RequestHeader(WebConstants.HEADER) String token,
            @RequestParam(defaultValue = "1") Integer current,@RequestParam(defaultValue = "10") Integer pageSize){
        try {
            AuthResult authResult = manageService.findByToken(token);
            String deptId = authResult.getUser().getDeptId();
            IPage<Person> iPage = personService.page(name,idCard,codeStatus,deptId,current,pageSize);
            PageVo<PersonVo> pageVo = new PageVo<>();
            List<PersonVo> list = new ArrayList<>();
            pageVo.setTotal(iPage.getTotal());
            pageVo.setCurrent(current);
            pageVo.setPageSize(pageSize);
            for (Person p : iPage.getRecords()){
                PersonVo pv = new PersonVo();
                BeanUtils.copyProperties(p,pv);
                pv.setShowOrigin(QueryUtils.append(pv,regionService));// 地址
                pv.setDeptName(deptInfoService.getNameByCode(p.getDeptId()));// 所属社区
                list.add(pv);
            }
            pageVo.setData(list);
            return ResponseData.ok(pageVo);
        }catch (IllegalArgumentException e){
            log.error("查询失败",e);
            return ResponseData.fail(e.getMessage());
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "社区用户新增或修改",notes = "web端社区用户新增或修改")
    @PostMapping("/save")
    public ResponseData<Object> save(@RequestBody PersonVo vo,@RequestHeader(WebConstants.HEADER) String token){
        try {
            AuthResult authResult = manageService.findByToken(token);
            String deptId = authResult.getUser().getDeptId();
            if (TcloudUtils.isNotEmpty(vo.getIdCard())){
                boolean variety = QueryUtils.idCardVerification(vo.getIdCard());
                if (!variety){
                    return ResponseData.fail("身份证不合法！请检查后再重试");
                }
            }
            personService.saveOrUpdate(vo,deptId);
            if (TcloudUtils.isEmpty(vo.getId())){
                return ResponseData.ok("添加成功！该账户登录账号为:"+vo.getIdCard()+"，默认登录密码为 "+password);
            }
            return ResponseData.ok("修改成功！");
        }catch (IllegalArgumentException e){
            log.error("社区用户新增或修改失败",e);
            return ResponseData.fail(e.getMessage());
        }catch (Exception e){
            log.error("社区用户新增或修改失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "社区用户删除",notes = "web端社区用户删除（逻辑删除）")
    @GetMapping("/{id}")
    public ResponseData<Object> del(@PathVariable String id){
        try {
            Person person = personService.getById(id);
            if (TcloudUtils.isEmpty(person)){
                return ResponseData.fail("需要删除的用户不存在！请确认信息再进行该操作！");
            }
            person.setDeleted(1);// 已删除
            personService.updateById(person);
            // 清空账户信息
            Account account = accountService.getByUid(person.getId());
            if (TcloudUtils.isNotEmpty(account)){
                accountService.removeById(account);
            }
            return ResponseData.ok("该用户已成功删除！");
        }catch (Exception e){
            log.error("删除失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "社区人员信息按条件导出",notes = "web端社区居民人员导出")
    @GetMapping("/download")
    public void download(
            @RequestParam(WebConstants.HEADER) String token, HttpServletResponse response,
            @RequestParam(required = false) String idCard, @RequestParam(required = false) String name,
            @RequestParam(required = false) String codeStatus){
        try {
            AuthResult authResult = manageService.findByToken(token);
            String deptId = authResult.getUser().getDeptId();
            List<Person> data = personService.getListByQuery(name,idCard,codeStatus,deptId);
            List<Map<String,Object>> maps = new ArrayList<>();
            Map<String, List<String>> param = PersonEnum.getFields();
            List<String> fields  = param.get("fields");
            List<String> values = param.get("values");
            // 封装excel表头
            List beanList = new ArrayList();
            for (int i=0 ; i<fields.size() ; ++i){
                beanList.add(new ExcelModel(values.get(i), fields.get(i)));
            }
            for (Person p : data){
                PersonVo pv = new PersonVo();
                BeanUtils.copyProperties(p,pv);
                if (TcloudUtils.isNotEmpty(p.getCodeStatus())){
                    switch (p.getCodeStatus()){
                        case 1 : pv.setCodeName("绿码");break;
                        case 2 : pv.setCodeName("黄码");break;
                        case 3 : pv.setCodeName("红码");break;
                        default: pv.setCodeName("无");break;
                    }
                }
                if (TcloudUtils.isNotEmpty(p.getIdCard())){
                    if (p.getIdCard().length() <= 14){
                        pv.setShowCard("不合法的身份证号码");
                    }else {
                        StringBuffer sb = new StringBuffer(p.getIdCard());
                        pv.setShowCard(sb.replace(6,14,"******").toString());
                    }
                }
                if (TcloudUtils.isNotEmpty(p.getProvince())){
                    p.setProvince(regionService.getCodeByName(p.getProvince()));
                }
                if (TcloudUtils.isNotEmpty(p.getCity())){
                    p.setCity(regionService.getCodeByName(p.getCity()));
                }
                if (TcloudUtils.isNotEmpty(p.getDistrict())){
                    p.setDistrict(regionService.getCodeByName(p.getDistrict()));
                }
                if (TcloudUtils.isNotEmpty(p.getStreet())){
                    p.setStreet(regionService.getCodeByName(p.getStreet()));
                }
                if (TcloudUtils.isNotEmpty(p.getVillage())){
                    p.setVillage(regionService.getCodeByName(p.getVillage()));
                }
                Map<String,Object> map = TcloudUtils.convertBean(pv);
                maps.add(map);
            }
            String file = "社区人员数据.xlsx";
            String title = "社区人员数据信息表";
            String fileName = new String(file.getBytes(), "ISO-8859-1");
            // excel数据输出
            GrainExcelUtils.getExcel(response, fileName, title, beanList, maps);
        }catch (IllegalArgumentException e){
            log.error("导出失败",e.getMessage());
        }catch (Exception e){
            log.error("导出失败",e);
        }
    }

    @ApiOperation(value = "根据Token查询该社区下的人员列表",notes = "web端查询社区居民列表")
    @GetMapping("/getPerson")
    public ResponseData<Object> getByDept(@RequestHeader(WebConstants.HEADER) String token,
                                          @RequestParam(required = false) String name){
        try {
            AuthResult authResult = manageService.findByToken(token);
            String deptId = authResult.getUser().getDeptId();
            QueryWrapper<Person> query = Wrappers.query();
            if (TcloudUtils.isNotEmpty(name)){
                query.like("name",name);
            }
            query.eq("deleted",0);// 在用的
            query.likeRight("dept_id",deptId);
            return ResponseData.ok(personService.list(query));
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail("查询失败");
        }
    }
}
