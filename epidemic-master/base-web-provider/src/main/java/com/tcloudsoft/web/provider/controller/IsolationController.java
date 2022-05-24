package com.tcloudsoft.web.provider.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tcloudsoft.feign.api.provider.IAuthService;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.common.PageVo;
import com.tcloudsoft.utils.common.UserVo;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.web.provider.model.Isolation;
import com.tcloudsoft.web.provider.model.IsolationExam;
import com.tcloudsoft.web.provider.model.IsolationType;
import com.tcloudsoft.web.provider.model.Vaccines;
import com.tcloudsoft.web.provider.service.IsolationExamService;
import com.tcloudsoft.web.provider.service.IsolationService;
import com.tcloudsoft.web.provider.service.IsolationTypeService;
import com.tcloudsoft.web.provider.vo.IsolationVo;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  隔离信息管理 前端控制器
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/isolation")
public class IsolationController {

    @Resource
    IsolationService isolationService;
    @Resource
    IAuthService iAuthService;
    @Resource
    IsolationTypeService isolationTypeService;
    @Resource
    IsolationExamService isolationExamService;

    @ApiOperation(value = "查询隔离记录列表",notes = "web端查询隔离记录列表")
    @GetMapping("/page")
    public ResponseData<PageVo<IsolationVo>> page(
            @RequestParam(required = false) String name, @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer status, @RequestHeader(WebConstants.HEADER) String token,
            @RequestParam(defaultValue = "1")Integer current,@RequestParam(defaultValue = "10")Integer pageSize){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            IPage<IsolationVo> iPage = isolationService.page(name,type,status,deptId,current,pageSize);
            PageVo<IsolationVo> pageVo = new PageVo();
            pageVo.setCurrent(current);
            pageVo.setPageSize(pageSize);
            pageVo.setTotal(iPage.getTotal());
            for (IsolationVo vo : iPage.getRecords()){
                if (TcloudUtils.isNotEmpty(vo.getType())){
                    IsolationType it = isolationTypeService.getById(vo.getType());
                    vo.setIsolationName(TcloudUtils.isEmpty(it)?"未知":it.getName());
                }
                // 查询该隔离记录是否还有待审核的信息
                List<IsolationExam> list = isolationExamService.getList(vo.getId(),0);
                if (TcloudUtils.isNotEmpty(list) && list.size()>0){
                    vo.setExam(true);
                }else {
                    vo.setExam(false);
                }
            }
            pageVo.setData(iPage.getRecords());
            return ResponseData.ok(pageVo);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "添加隔离记录",notes = "web端添加隔离记录")
    @PostMapping("/save")
    public ResponseData<String> save(@RequestBody IsolationVo vo,@RequestHeader(WebConstants.HEADER) String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = TcloudUtils.isEmpty(vo.getDeptId())?userVo.getDeptId():vo.getDeptId();
            Isolation isolation = new IsolationVo();
            BeanUtils.copyProperties(vo,isolation);
            Date date = new Date();
            if (TcloudUtils.isEmpty(isolation.getId())){
                isolation.setCreatorTime(date);
            }
            isolation.setTime(date);// 更新开始隔离时间
            isolation.setDeptId(deptId);
            if (TcloudUtils.isEmpty(isolation.getType())){
                return ResponseData.fail("隔离阶段不可为空！");
            }
            IsolationType isolationType = isolationTypeService.getById(isolation.getType());
            Integer day = 0;
            if (TcloudUtils.isNotEmpty(isolationType)){
                day = TcloudUtils.isEmpty(isolationType.getDayNum())?0:isolationType.getDayNum();
            }
            // 获取本阶段预计解除隔离时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND,24*60*60*day);
            isolation.setPlanCloseTime(calendar.getTime());// 预计解除时间
            isolationService.saveOrUpdate(isolation);
            return ResponseData.ok("添加成功!");
        }catch (Exception e){
            log.error("添加失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "添加隔离记录",notes = "微信端端添加隔离记录")
    @PostMapping("/wxSave")
    public ResponseData<String> wxSave(@RequestBody IsolationVo vo){
        try {
            String deptId = vo.getDeptId();
            if (TcloudUtils.isEmpty(deptId)){
                return ResponseData.fail("社区不能为空！");
            }
            Isolation isolation = new IsolationVo();
            BeanUtils.copyProperties(vo,isolation);
            Date date = new Date();
            if (TcloudUtils.isEmpty(isolation.getId())){
                isolation.setCreatorTime(date);
            }
            isolation.setTime(date);// 更新开始隔离时间
            isolation.setDeptId(deptId);
            if (TcloudUtils.isEmpty(isolation.getType())){
                return ResponseData.fail("隔离阶段不可为空！");
            }
            IsolationType isolationType = isolationTypeService.getById(isolation.getType());
            Integer day = 0;
            if (TcloudUtils.isNotEmpty(isolationType)){
                day = TcloudUtils.isEmpty(isolationType.getDayNum())?0:isolationType.getDayNum();
            }
            // 获取本阶段预计解除隔离时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND,24*60*60*day);
            isolation.setPlanCloseTime(calendar.getTime());// 预计解除时间
            isolationService.saveOrUpdate(isolation);
            return ResponseData.ok("添加成功!");
        }catch (Exception e){
            log.error("添加失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "删除隔离记录",notes = "web端删除隔离记录")
    @GetMapping("/{id}")
    public ResponseData<String> del(@PathVariable String id){
        try {
            Isolation isolation = isolationService.getById(id);
            if (TcloudUtils.isEmpty(isolation)){
                return ResponseData.fail("需要删除的记录不存在！");
            }
            // 查询该隔离记录是否还有未审核的审核信息
            List<IsolationExam> doList = isolationExamService.getList(id,0);
            if (TcloudUtils.isNotEmpty(doList) && doList.size()>0){
                return ResponseData.fail("该审核记录还有未审核的审核申请，暂不可删除！");
            }
            isolationService.removeById(isolation);
            List<IsolationExam> list = isolationExamService.getList(id,null);
            for (IsolationExam exam : list){
                // 将历史申请解除的记录也删除
                isolationService.removeById(exam);
            }
            return ResponseData.ok("删除成功！");
        }catch (Exception e){
            log.error("删除失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

}
