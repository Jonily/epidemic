package com.tcloudsoft.web.provider.controller;


import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.web.provider.mapper.IsolationExamMapper;
import com.tcloudsoft.web.provider.mapper.IsolationMapper;
import com.tcloudsoft.web.provider.model.Isolation;
import com.tcloudsoft.web.provider.model.IsolationExam;
import com.tcloudsoft.web.provider.model.IsolationType;
import com.tcloudsoft.web.provider.service.IsolationExamService;
import com.tcloudsoft.web.provider.service.IsolationService;
import com.tcloudsoft.web.provider.service.IsolationTypeService;
import com.tcloudsoft.web.provider.vo.IsolationExamVo;
import com.tcloudsoft.web.provider.vo.IsolationVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2021-12-09
 */
@Slf4j
@RestController
@RequestMapping("/isolationExam")
public class IsolationExamController {

    @Resource
    IsolationExamService isolationExamService;
    @Resource
    IsolationService isolationService;
    @Resource
    IsolationExamMapper isolationExamMapper;
    @Resource
    IsolationTypeService isolationTypeService;
    @Resource
    IsolationMapper isolationMapper;

    @ApiOperation(value = "根据隔离记录ID查询其待审核的记录",notes = "web端查询待审核信息")
    @GetMapping("/get")
    public ResponseData<IsolationExamVo> get(@RequestParam String id){
        try {
            List<IsolationExam> list = isolationExamService.getList(id,0);
            IsolationExam isolationExam = new IsolationExam();
            if (TcloudUtils.isNotEmpty(list) && list.size() > 0){
                isolationExam = list.get(0);
            }
            IsolationExamVo isolationVo = new IsolationExamVo();
            if (TcloudUtils.isNotEmpty(isolationExam) && TcloudUtils.isNotEmpty(isolationExam.getId())){
                BeanUtils.copyProperties(isolationExam,isolationVo);
                if (TcloudUtils.isNotEmpty(isolationExam.getIsolationId())){
                    Isolation isolation = isolationService.getById(isolationExam.getIsolationId());
                    if (TcloudUtils.isNotEmpty(isolation)){
                        // 查询解除后的隔离状态
                        IsolationType isolationType = isolationTypeService.getById(isolation.getType());
                        IsolationType next = isolationTypeService.get(isolationType.getSort()+1);
                        isolationVo.setType(TcloudUtils.isEmpty(next)?"":next.getName());

                        // 查询申请人详细信息
                        IsolationExamVo examVo = isolationMapper.getPerson(isolation.getIdCard());
                        isolationVo.setName(examVo.getName());
                        isolationVo.setIdCard(examVo.getIdCard());
                        isolationVo.setPhone(examVo.getPhone());
                    }
                }
            }
            return ResponseData.ok(isolationVo);
        }catch (Exception e){
            log.error("查询待审核信息失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "提交解除申请",notes = "微信端提交解除申请")
    @PostMapping("/save")
    public ResponseData<String> save(@RequestBody IsolationExamVo vo){
        try {
            Isolation isolation1 = isolationExamService.getByIdCard(vo.getIdCard());
            if (TcloudUtils.isEmpty(isolation1)){
                return ResponseData.fail("隔离记录不存在，无法提交申请，请先进行隔离登记！");
            }else {
                vo.setIsolationId(isolation1.getId());
            }
            IsolationExam isolationExam = new IsolationExam();
            BeanUtils.copyProperties(vo,isolationExam);
            Isolation isolation = isolationService.getById(vo.getIsolationId());
            if ((TcloudUtils.isNotEmpty(isolation) && TcloudUtils.isNotEmpty(isolation.getCloseTime())) && !isolation.getType().equals(vo.getType())){
                return ResponseData.fail("当前隔离等级已解除，无需再提交解除申请！");
            }
            List<IsolationExam> list = isolationExamService.getList(vo.getIsolationId(),0);
            if (TcloudUtils.isNotEmpty(list) && list.size() > 0){
                return ResponseData.fail("该隔离对象还有未审核的解除申请记录，请勿重复提交！");
            }
            isolationExam.setCreatorTime(new Date());
            isolationExam.setStatus(0);
            isolationExamService.save(isolationExam);
            return ResponseData.ok("提交成功");
        }catch (Exception e){
            log.error("提交申请失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "撤销申请",notes = "微信端撤销解除申请")
    @GetMapping("/{id}")
    public ResponseData<String> del(@PathVariable String id){
        try {
            IsolationExam isolationExam = isolationExamService.getById(id);
            if (TcloudUtils.isEmpty(isolationExam)){
                return ResponseData.fail("撤销对象不存在！");
            }
            if (1 == isolationExam.getStatus()){
                return ResponseData.fail("该记录已审核，暂不可撤销！");
            }
            isolationService.removeById(isolationExam);
            return ResponseData.ok("撤销成功！");
        }catch (Exception e){
            log.error("撤销失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "审核解除申请",notes = "web端审核解除申请")
    @GetMapping("/exam")
    public ResponseData<String> exam(@RequestParam String id){
        try {
            IsolationExam exam = isolationExamService.getById(id);
            if (TcloudUtils.isEmpty(exam)){
                return ResponseData.fail("审核对象不存在！");
            }
            if (1 == exam.getStatus()){
                return ResponseData.fail("该申请已审核，请勿重复审核！");
            }
            exam.setStatus(1);
            isolationExamService.updateById(exam);
            Date date = new Date();
            // 更新隔离状态
            Isolation isolation = isolationService.getById(exam.getIsolationId());
            isolation.setTime(date);// 更新隔离开始时间
            IsolationType isolationType = isolationTypeService.getById(isolation.getType());
            IsolationType next = isolationTypeService.get(isolationType.getSort()+1);// 下一个流程
            if (TcloudUtils.isEmpty(next) || 4 == next.getSort()){// 最后一个流程
                isolation.setCloseTime(new Date());// 隔离结束时间
                isolation.setType(next.getId());
                isolation.setPlanCloseTime(date);// 预计结束时间就是开始时间
            }else {// 如果下一个流程不是最后一个流程
                // 获取本阶段预计解除隔离时间
                Integer day = next.getDayNum();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.SECOND,24*60*60*day);
                isolation.setType(next.getId());
                isolation.setPlanCloseTime(calendar.getTime());// 预计解除时间
            }
            isolationService.updateById(isolation);
            return ResponseData.ok("已审核！");
        }catch (Exception e){
            log.error("审核失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }
}
