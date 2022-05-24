package com.tcloudsoft.web.provider.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tcloudsoft.feign.api.provider.IAuthService;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.common.PageVo;
import com.tcloudsoft.utils.common.UserVo;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.utils.utils.DateUtils;
import com.tcloudsoft.web.provider.mapper.VisitMapper;
import com.tcloudsoft.web.provider.model.Visit;
import com.tcloudsoft.web.provider.model.VisitType;
import com.tcloudsoft.web.provider.service.VisitService;
import com.tcloudsoft.web.provider.service.VisitTypeService;
import com.tcloudsoft.web.provider.util.CovertUtils;
import com.tcloudsoft.web.provider.util.QueryUtils;
import com.tcloudsoft.web.provider.vo.VisitVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *   社区人员出入管理   前端控制器
 * </p>
 *
 * @since 2021-12-06
 */
@Slf4j
@RestController
@RequestMapping("/visit")
public class VisitController {

    @Resource
    VisitService visitService;
    @Resource
    IAuthService iAuthService;
    @Resource
    VisitMapper visitMapper;
    @Resource
    VisitTypeService visitTypeService;


    @ApiOperation(value = "分页查询社区人员行程",notes = "web端查询社区人员行程")
    @GetMapping("/page")
    public ResponseData<PageVo<VisitVo>> page(
            @RequestHeader(WebConstants.HEADER) String token,
            @RequestParam(required = false)String idCard, @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate, @RequestParam(required = false) Integer type,
            @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            IPage<VisitVo> iPage = visitService.page(idCard,type,startDate,endDate,deptId,current,pageSize);
            PageVo<VisitVo> pageVo = new PageVo();
            pageVo.setCurrent(current);
            pageVo.setPageSize(pageSize);
            pageVo.setTotal(iPage.getTotal());
            for (VisitVo visitVo : iPage.getRecords()) {
                VisitType visitType = visitTypeService.getById(visitVo.getFromModel());
                visitVo.setFromModel(TcloudUtils.isEmpty(visitType)?"未知":visitType.getName());
                visitVo.setFromAddress(QueryUtils.appendFrom(visitVo,visitMapper));
                visitVo.setTargetAddress(QueryUtils.appendTarget(visitVo,visitMapper));
            }
            pageVo.setData(iPage.getRecords());
            return ResponseData.ok(pageVo);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "社区人员行程记录删除",notes = "web端删除社区人员行程记录")
    @GetMapping("/{id}")
    public ResponseData<String> del(@PathVariable String id){
        try {
            Visit visit = visitService.getById(id);
            if (TcloudUtils.isEmpty(visit)){
                return ResponseData.fail("删除对象不存在！");
            }
            visit.setDeleted(1);// 已删除
            visitService.updateById(visit);
            return ResponseData.ok("删除成功");
        }catch (Exception e){
            log.error("删除失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "社区人员行程详情",notes = "web端社区人员行程详情")
    @GetMapping("/details")
    public ResponseData<VisitVo> details(@RequestParam String id){
        try{
            VisitVo vo = visitMapper.getByQuery(id);
            if (TcloudUtils.isEmpty(vo)){
                return ResponseData.fail("查询对象不存在！");
            }
            if (TcloudUtils.isNotEmpty(vo.getIdCard()) && vo.getIdCard().length() > 16){
                vo.setIdCard(vo.getIdCard().replace(vo.getIdCard().substring(4, 16), "**************"));
            }
            VisitType visitType = visitTypeService.getById(vo.getFromModel());
            vo.setFromModel(TcloudUtils.isEmpty(visitType)?"未知":visitType.getName());
            vo.setFromAddress(QueryUtils.appendFrom(vo,visitMapper));
            vo.setTargetAddress(QueryUtils.appendTarget(vo,visitMapper));
            if (TcloudUtils.isNotEmpty(vo.getCodeStatus())){
                switch (vo.getCodeStatus()){
                    case 1:vo.setCodeName("绿码");break;
                    case 2:vo.setCodeName("黄码");break;
                    case 3:vo.setCodeName("红码");break;
                }
            }
            return ResponseData.ok(vo);
        }catch (Exception e) {
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "社区人员行程总数统计",notes = "web端社区人员总数行程统计")
    @GetMapping("/statistic")
    public ResponseData<JSONObject> statistic(@RequestHeader(WebConstants.HEADER)String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            JSONObject jsonObject = new JSONObject();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String time = simpleDateFormat.format(date);
            Integer count1 = TcloudUtils.isEmpty(visitService.getVisitCount(time,deptId))?0:visitService.getVisitCount(time,deptId);
            jsonObject.put("visitCount",count1);
            Integer count2 = TcloudUtils.isEmpty(visitService.getTripCount(time,deptId))?0:visitService.getTripCount(time,deptId);
            jsonObject.put("tripCount",count2);
            Integer count3 = TcloudUtils.isEmpty(visitService.getVisitCountHistory(deptId))?0:visitService.getVisitCountHistory(deptId);
            jsonObject.put("visitCountHistory",count3);
            Integer count4 = TcloudUtils.isEmpty(visitService.getTripCountHistory(deptId))?0:visitService.getTripCountHistory(deptId);
            jsonObject.put("tripCountHistory",count4);
            return ResponseData.ok(jsonObject);
        } catch (Exception e) {
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "今日来访人员名单",notes = "web端今日来访人员名单")
    @GetMapping("/todayVisitList")
    public ResponseData<List<VisitVo>> todayVisitList(@RequestHeader(WebConstants.HEADER)String token){
        try{
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            String time = DateUtil.format(new Date(),"yyyy-MM-dd");
            List<VisitVo> list = visitService.getTodayVisitList(time,deptId);
            for (VisitVo vo : list){
                vo.setFromAddress(QueryUtils.appendFrom(vo,visitMapper));
            }
            return ResponseData.ok(list);
        } catch (Exception e) {
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "今日出行人员名单",notes = "web端今日出行人员名单")
    @GetMapping("/todayTripList")
    public ResponseData<List<VisitVo>> todayTripList(@RequestHeader(WebConstants.HEADER)String token){
        try{
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            String time = DateUtil.format(new Date(),"yyyy-MM-dd");
            List<VisitVo> list = visitService.getTodayTripList(time,deptId);
            for (VisitVo vo : list){
                vo.setTargetAddress(QueryUtils.appendTarget(vo,visitMapper));
            }
            return ResponseData.ok(list);
        } catch (Exception e) {
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "出入占比",notes = "web端查询出入占比统计图")
    @GetMapping("/inOut")
    public ResponseData<JSONArray> inOut(@RequestHeader(WebConstants.HEADER)String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            Integer inNum = visitService.getVisitCountHistory(deptId);
            inNum = TcloudUtils.isEmpty(inNum)?0:inNum;
            Integer outNum = visitService.getTripCountHistory(deptId);
            outNum = TcloudUtils.isEmpty(outNum)?0:outNum;
            Integer sumNum = inNum + outNum;
            Double inPer = CovertUtils.percent(inNum,sumNum);
            Double outPer = 0d;
            if (0 != outNum){
                outPer = 100 - inPer;
            }
            JSONArray jsonArray = new JSONArray();
            JSONObject in = new JSONObject();
            in.put("name","来访");
            in.put("value",inNum);
            jsonArray.add(in);
            JSONObject out = new JSONObject();
            out.put("name","出行");
            out.put("value",outNum);
            jsonArray.add(out);
            return ResponseData.ok(jsonArray);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "出入人员排行榜",notes = "web端查询出入人员排行榜")
    @GetMapping("/ranking")
    public ResponseData<JSONArray> rankIng(@RequestHeader(WebConstants.HEADER)String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            List<Map<String,Object>> mapList = visitMapper.selectRankIng(deptId);
            JSONArray jsonArray = new JSONArray();
            if (TcloudUtils.isNotEmpty(mapList) && mapList.size() > 0){
                for (Map<String,Object> rankIng : mapList){
                    JSONObject json = new JSONObject();
                    json.put("idCard",rankIng.get("idCard"));
                    String name = visitMapper.selectPersonName(rankIng.get("idCard").toString());
                    json.put("name",name);
                    json.put("count", rankIng.get("count"));
                    jsonArray.add(json);
                }
            }
            return ResponseData.ok(jsonArray);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "微信端行程新增",notes = "微信端行程新增")
    @PostMapping("/save")
    public ResponseData<String> save(@RequestBody VisitVo vo){
        try {
            Visit visit = new Visit();
            BeanUtils.copyProperties(vo,visit);
            if (TcloudUtils.isEmpty(vo.getDeptId())){
                return ResponseData.fail("社区不能为空！");
            }
            visit.setDeleted(0);
            visit.setCreatorTime(new Date());
            visitService.save(visit);
            return ResponseData.ok("新增成功");
        }catch (Exception e){
            log.error("行程新增失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

}
