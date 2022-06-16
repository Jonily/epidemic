package com.tcloudsoft.web.provider.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.feign.api.provider.IAuthService;
import com.tcloudsoft.utils.Excel.ExcelModel;
import com.tcloudsoft.utils.Excel.GrainExcelUtils;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.common.PageVo;
import com.tcloudsoft.utils.common.UserVo;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.ex.TcmsAuthException;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.web.provider.enumType.VaccinationEnum;
import com.tcloudsoft.web.provider.mapper.VaccinationMapper;
import com.tcloudsoft.web.provider.model.Vaccination;
import com.tcloudsoft.web.provider.model.Vaccines;
import com.tcloudsoft.web.provider.service.VaccinationService;
import com.tcloudsoft.web.provider.service.VaccinesService;
import com.tcloudsoft.web.provider.util.CovertUtils;
import com.tcloudsoft.web.provider.vo.VaccinationVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  疫苗接种管理 前端控制器
 * </p>
 *
 * @since 2021-12-06
 */
@Slf4j
@RestController
@RequestMapping("/vaccination")
public class VaccinationController {

    @Resource
    VaccinationService vaccinationService;
    @Resource
    IAuthService iAuthService;
    @Resource
    VaccinesService vaccinesService;
    @Resource
    VaccinationMapper vaccinationMapper;

    @ApiOperation(value = "分页查询疫苗接种情况登记记录",notes = "web端查询疫苗接种列表")
    @GetMapping("/page")
    public ResponseData<PageVo<VaccinationVo>> page(
            @RequestParam(required = false)String idCard, @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer type,@RequestHeader(WebConstants.HEADER) String token,
            @RequestParam(defaultValue = "1") Integer current,@RequestParam(defaultValue = "10") Integer pageSize){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            IPage<VaccinationVo> iPage = vaccinationService.page(idCard,brand,type,deptId,current,pageSize);
            PageVo<VaccinationVo> pageVo = new PageVo();
            pageVo.setCurrent(current);
            pageVo.setPageSize(pageSize);
            pageVo.setTotal(iPage.getTotal());
            for (VaccinationVo vo : iPage.getRecords()){
                if (TcloudUtils.isNotEmpty(vo.getBrand())){
                    Vaccines vaccines = vaccinesService.getById(vo.getBrand());
                    vo.setVaccinesName(TcloudUtils.isEmpty(vaccines)?"未知":vaccines.getName());
                }
            }
            pageVo.setData(iPage.getRecords());
            return ResponseData.ok(pageVo);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }



    @ApiOperation(value = "疫苗接种登记填报",notes = "web端疫苗接种登记")
    @PostMapping("/save")
    public ResponseData<String> save(@RequestBody VaccinationVo vo,@RequestHeader(WebConstants.HEADER) String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = TcloudUtils.isNotEmpty(vo.getDeptId())?vo.getDeptId():userVo.getDeptId();
            Vaccination mode = new Vaccination();
            BeanUtils.copyProperties(vo,mode);
            if (TcloudUtils.isNotEmpty(vo.getId())){// 修改时
                if (TcloudUtils.isEmpty(mode.getTime())){
                    mode.setTime(new Date());
                }
                mode.setDeptId(deptId);// 更新部门
                // 如果已接种完成
                if (TcloudUtils.isNotEmpty(vo.getBrand()) && TcloudUtils.isNotEmpty(vo.getInoculationNum())){
                    Vaccines vaccines = vaccinesService.getById(vo.getBrand());
                    if (vo.getInoculationNum() >= vaccines.getNumber()){
                        mode.setFinishTime(new Date());
                    }
                }
                vaccinationService.updateById(mode);
            }else {
                // 查询该人是否已接种过该品种的疫苗
                List<Vaccination> list = vaccinationService.getByQuery(vo.getIdCard(),vo.getBrand());
                if (TcloudUtils.isEmpty(list) || list.size() == 0){
                    if (TcloudUtils.isNotEmpty(vo.getInoculationNum()) && vo.getInoculationNum() != 1){
                        return ResponseData.fail("该居民还未接种该品种的疫苗，请从第一针开始填报！");
                    }else {
                        mode.setInoculationNum(1);// 默认为第一针
                    }
                    mode.setDeptId(deptId);
                    mode.setCreatorTime(new Date());
                    mode.setStatus(1);// 已审核
                    mode.setDeleted(0);
                    vaccinationService.save(mode);
                }else {// 如果该人已经接种过该品种的疫苗
                    Vaccination vaccination = list.get(0);
                    if (TcloudUtils.isNotEmpty(vo.getInoculationNum()) && vo.getInoculationNum() <= vaccination.getInoculationNum()){
                        return ResponseData.fail("该居民已经接种该疫苗的第"+vaccination.getInoculationNum()+"针，无法再接种第"+vo.getInoculationNum()+"针，请重新选择！");
                    }else if (TcloudUtils.isNotEmpty(vo.getInoculationNum()) && vo.getInoculationNum() > vaccination.getInoculationNum()+1 ){
                        return ResponseData.fail("该居民已接种该疫苗的第"+vaccination.getInoculationNum()+"针，不能直接跳过第"+(vaccination.getInoculationNum()+1)+"针去接种第"+vo.getInoculationNum()+"针！");
                    }else if (TcloudUtils.isNotEmpty(vo.getInoculationNum())){
                        Vaccines vaccines = vaccinesService.getById(vaccination.getBrand());
                        if (TcloudUtils.isNotEmpty(vaccines) && TcloudUtils.isNotEmpty(vaccines.getNumber()) && vaccines.getNumber() <= vaccination.getInoculationNum()){
                            return ResponseData.fail("该居民已完成该品种疫苗的接种工作，暂不可再进行接种登记！");
                        }
                    }
                    vaccination.setInoculationNum(vaccination.getInoculationNum() + 1);
                    // 接种完成后 如果次数已经达到该疫苗的最大接种数
                    Vaccines vaccines = vaccinesService.getById(vaccination.getBrand());
                    if (vaccines.getNumber() <= vaccination.getInoculationNum()){
                        vaccination.setFinishTime(new Date());
                    }
                    if (TcloudUtils.isNotEmpty(vo.getArea())) vaccination.setArea(vo.getArea());
                    if (TcloudUtils.isNotEmpty(vo.getTime())) vaccination.setTime(vo.getTime());
                    // 更新记录
                    vaccination.setDeptId(deptId);
                    vaccinationService.updateById(vaccination);
                }
            }
            return ResponseData.ok("登记成功");
        }catch (Exception e){
            log.error("登记失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "疫苗接种登记填报",notes = "微信端端疫苗接种登记")
    @PostMapping("/wxSave")
    public ResponseData<String> wxSave(@RequestBody VaccinationVo vo){
        try {
            String deptId = vo.getDeptId();
            if (TcloudUtils.isEmpty(deptId)){
                return ResponseData.fail("社区不能为空！");
            }
            Vaccination mode = new Vaccination();
            BeanUtils.copyProperties(vo,mode);
            if (TcloudUtils.isNotEmpty(vo.getId())){// 修改时
                if (TcloudUtils.isEmpty(mode.getTime())){
                    mode.setTime(new Date());
                }
                mode.setDeptId(deptId);// 更新部门
                // 如果已接种完成
                if (TcloudUtils.isNotEmpty(vo.getBrand()) && TcloudUtils.isNotEmpty(vo.getInoculationNum())){
                    Vaccines vaccines = vaccinesService.getById(vo.getBrand());
                    if (vo.getInoculationNum() >= vaccines.getNumber()){
                        mode.setFinishTime(new Date());
                    }
                }
                vaccinationService.updateById(mode);
            }else {
                // 查询该人是否已接种过该品种的疫苗
                List<Vaccination> list = vaccinationService.getByQuery(vo.getIdCard(),vo.getBrand());
                if (TcloudUtils.isEmpty(list) || list.size() == 0){
                    if (TcloudUtils.isNotEmpty(vo.getInoculationNum()) && vo.getInoculationNum() != 1){
                        return ResponseData.fail("该居民还未接种该品种的疫苗，请从第一针开始填报！");
                    }else {
                        mode.setInoculationNum(1);// 默认为第一针
                    }
                    mode.setDeptId(deptId);
                    mode.setCreatorTime(new Date());
                    mode.setStatus(0);// 未审核
                    mode.setDeleted(0);
                    vaccinationService.save(mode);
                }else {// 如果该人已经接种过该品种的疫苗
                    Vaccination vaccination = list.get(0);
                    if (TcloudUtils.isNotEmpty(vo.getInoculationNum()) && vo.getInoculationNum() <= vaccination.getInoculationNum()){
                        return ResponseData.fail("该居民已经接种该疫苗的第"+vaccination.getInoculationNum()+"针，无法再接种第"+vo.getInoculationNum()+"针，请重新选择！");
                    }else if (TcloudUtils.isNotEmpty(vo.getInoculationNum()) && vo.getInoculationNum() > vaccination.getInoculationNum()+1 ){
                        return ResponseData.fail("该居民已接种该疫苗的第"+vaccination.getInoculationNum()+"针，不能直接跳过第"+(vaccination.getInoculationNum()+1)+"针去接种第"+vo.getInoculationNum()+"针！");
                    }else if (TcloudUtils.isNotEmpty(vo.getInoculationNum())){
                        Vaccines vaccines = vaccinesService.getById(vaccination.getBrand());
                        if (TcloudUtils.isNotEmpty(vaccines) && TcloudUtils.isNotEmpty(vaccines.getNumber()) && vaccines.getNumber() <= vaccination.getInoculationNum()){
                            return ResponseData.fail("该居民已完成该品种疫苗的接种工作，暂不可再进行接种登记！");
                        }
                    }
                    vaccination.setInoculationNum(vaccination.getInoculationNum() + 1);
                    // 接种完成后 如果次数已经达到该疫苗的最大接种数
                    Vaccines vaccines = vaccinesService.getById(vaccination.getBrand());
                    if (vaccines.getNumber() <= vaccination.getInoculationNum()){
                        vaccination.setFinishTime(new Date());
                    }
                    if (TcloudUtils.isNotEmpty(vo.getArea())) vaccination.setArea(vo.getArea());
                    if (TcloudUtils.isNotEmpty(vo.getTime())) vaccination.setTime(vo.getTime());
                    // 更新记录
                    vaccination.setDeptId(deptId);
                    vaccinationService.updateById(vaccination);
                }
            }
            return ResponseData.ok("登记成功");
        }catch (Exception e){
            log.error("登记失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "疫苗接种申请审核",notes = "web端疫苗接种登记数据审核")
    @GetMapping("/examine")
    public ResponseData<String> examine(@RequestParam String id){
        try {
            Vaccination vaccination = vaccinationService.getById(id);
            if (TcloudUtils.isEmpty(vaccination)){
                return ResponseData.fail("审核对象不存在！");
            }
            vaccination.setStatus(1);// 已审核
            vaccinationService.updateById(vaccination);
            return ResponseData.ok("审核成功");
        }catch (Exception e){
            log.error("审核失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "疫苗接种记录删除",notes = "web端删除疫苗接种记录")
    @GetMapping("/{id}")
    public ResponseData<String> del(@PathVariable String id){
        try {
            Vaccination vaccination = vaccinationService.getById(id);
            if (TcloudUtils.isEmpty(vaccination)){
                return ResponseData.fail("删除对象不存在！");
            }
            if (1 == vaccination.getStatus()){
                return ResponseData.fail("该记录已被审核，暂不可删除！");
            }
            vaccination.setDeleted(1);// 已删除
            vaccinationService.updateById(vaccination);
            return ResponseData.ok("删除成功");
        }catch (Exception e){
            log.error("删除失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "疫苗接种情况导出",notes = "web端疫苗接种列表导出")
    @GetMapping("/download")
    public void download(@RequestParam(WebConstants.HEADER) String token, HttpServletResponse response,
                         @RequestParam(required = false) String idCard, @RequestParam(required = false) String brand,
                         @RequestParam(required = false) Integer type){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            List<VaccinationVo> vos = vaccinationService.getList(idCard,brand,type,deptId);
            List<Map<String,Object>> maps = new ArrayList<>();
            Map<String,List<String>> param = VaccinationEnum.getFields();
            List<String> fields  = param.get("fields");
            List<String> values = param.get("values");
            // 封装excel表头
            List beanList = new ArrayList();
            for (int i=0 ; i<fields.size() ; ++i){
                beanList.add(new ExcelModel(values.get(i), fields.get(i)));
            }
            for (VaccinationVo vo : vos) {
                if (TcloudUtils.isNotEmpty(vo.getBrand())){
                    Vaccines vaccines = vaccinesService.getById(vo.getBrand());
                    vo.setVaccinesName(TcloudUtils.isEmpty(vaccines)?"":vaccines.getName());
                }
                if (TcloudUtils.isNotEmpty(vo.getIdCard())){
                    if (vo.getIdCard().length() <= 14){
                        vo.setIdCard("不合法的身份证号码");
                    }else {
                        StringBuffer sb = new StringBuffer(vo.getIdCard());
                        vo.setIdCard(sb.replace(6,14,"******").toString());
                    }
                }
                if (TcloudUtils.isNotEmpty(vo.getStatus())){
                    switch (vo.getStatus()){
                        case 0 : vo.setStatusName("未审核");break;
                        case 1 : vo.setStatusName("已审核");break;
                    }
                }
                Map<String,Object> map = TcloudUtils.convertBean(vo);
                maps.add(map);
            }
            String file = "疫苗接种情况数据.xlsx";
            String title = "疫苗接种情况数据";
            String fileName = new String(file.getBytes(), "ISO-8859-1");
            // excel数据输出
            GrainExcelUtils.getExcel(response, fileName, title, beanList, maps);
        }catch (Exception e){
            log.error("导出失败",e);
        }
    }

    @ApiOperation(value = "查询统计数据-数量统计",notes = "web端统计--数量统计")
    @GetMapping("/one")
    public ResponseData<JSONObject> jsonOne(@RequestHeader(WebConstants.HEADER)String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            JSONObject json = new JSONObject();
            int count = vaccinationService.count(deptId,1);
            json.put("hisCount",count);// 总次数
            int count2 = vaccinationService.count(deptId,2);
            json.put("hisFinCount",count2);// 总完成数
            int count3 = vaccinationService.count(deptId,3);
            json.put("toDayCount",count3);// 今日新增次数
            int count4 = vaccinationService.count(deptId,4);
            json.put("toDayFinCount",count4);// 今日完成数
            return ResponseData.ok(json);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "查询统计数据-未完成接种人员中年龄所占比例",notes = "web端统计--未完成接种人员钟年龄所占比例")
    @GetMapping("/two")
    public ResponseData<JSONObject> jsonTwo(@RequestHeader(WebConstants.HEADER)String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            JSONObject json = new JSONObject();
            QueryWrapper<Vaccination> query = Wrappers.query();
            query.eq("dept_id",deptId);
            query.eq("deleted",0);
            Integer count = vaccinationService.count(query);
            Integer countAdd = 0;
            Double perAdd = 0d;
            if (TcloudUtils.isEmpty(count)) count = 0;
            // 10岁以下
            Integer count2To10 = vaccinationService.count(deptId,null,10);
            countAdd += count2To10;
            JSONObject json1 = new JSONObject();
            json1.put("count",count2To10);
            Double per1 = CovertUtils.percent(count2To10,count);
            perAdd += per1;
            json1.put("per", per1);
            json.put("10岁以下",json1);
            // 10-18 岁
            Integer count10To18 = vaccinationService.count(deptId,10,18);
            countAdd += count10To18;
            JSONObject json2 = new JSONObject();
            json2.put("count",count10To18);
            Double per2 = CovertUtils.percent(count10To18,count);
            perAdd += per2;
            json2.put("per", per2);
            json.put("10-18岁",json2);
            // 18-35 岁
            Integer count18To35 = vaccinationService.count(deptId,18,35);
            countAdd += count18To35;
            JSONObject json3 = new JSONObject();
            json3.put("count",count18To35);
            Double per3 = CovertUtils.percent(count18To35,count);
            perAdd += per3;
            json3.put("per", per3);
            json.put("18-35岁",json3);
            // 35-55 岁
            Integer count35To55 = vaccinationService.count(deptId,35,55);
            countAdd += count35To55;
            JSONObject json4 = new JSONObject();
            json4.put("count",count35To55);
            Double per4 = CovertUtils.percent(count35To55,count);
            perAdd += per4;
            json4.put("per", per4);
            json.put("35-55岁",json4);
            // 55-80 岁
            Integer count55To80 = vaccinationService.count(deptId,55,80);
            countAdd += count55To80;
            JSONObject json5 = new JSONObject();
            json5.put("count",count55To80);
            Double per5 = CovertUtils.percent(count55To80,count);
            perAdd += per5;
            json5.put("per", per5);
            json.put("55-80岁",json5);
            // 80岁及以上
            Integer count80 = vaccinationService.count(deptId,80,0);
            countAdd += count80;
            JSONObject json6 = new JSONObject();
            json6.put("count",count80);
            Double per6 = CovertUtils.percent(count80,count);
            perAdd += per6;
            json6.put("per", per6);
            json.put("80岁及以上",json6);
            // 其他
            JSONObject jsonOther = new JSONObject();
            jsonOther.put("count",countAdd>=count?0:(count - countAdd));
            jsonOther.put("per",perAdd >= 100?0:(100 - perAdd));
            json.put("接种完成",jsonOther);
            return ResponseData.ok(json);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "查询统计数据--未接种人员健康码所占比例",notes = "查询统计数据--web端未接种人员中健康码所占比例")
    @GetMapping("three")
    public ResponseData<JSONObject> jsonThree(@RequestHeader(WebConstants.HEADER)String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            JSONObject json = new JSONObject();
            QueryWrapper<Vaccination> query = Wrappers.query();
            query.eq("dept_id",deptId);
            query.eq("deleted",0);
            Integer count = vaccinationService.count(query);
            Integer countAdd = 0;
            Double perAdd = 0d;
            // 绿码
            Integer count1 = vaccinationService.countCode(deptId,1);
            countAdd += count1;
            JSONObject json1 = new JSONObject();
            json1.put("count",count1);
            Double per1 = CovertUtils.percent(count1,count);
            perAdd += per1;
            json1.put("per", per1);
            json.put("绿码",json1);
            // 黄码
            Integer count2 = vaccinationService.countCode(deptId,2);
            countAdd += count2;
            JSONObject json2 = new JSONObject();
            json2.put("count",count2);
            Double per2 = CovertUtils.percent(count2,count);
            perAdd += per2;
            json2.put("per", per2);
            json.put("黄码",json2);
            // 红码
            Integer count3 = vaccinationService.countCode(deptId,3);
            countAdd += count3;
            JSONObject json3 = new JSONObject();
            json3.put("count",count3);
            Double per3 = CovertUtils.percent(count3,count);
            perAdd += per3;
            json3.put("per", per3);
            json.put("红码",json3);
            // 其他
            JSONObject jsonOther = new JSONObject();
            jsonOther.put("count",countAdd>=count?0:(count - countAdd));
            jsonOther.put("per",perAdd >= 100?0:(100 - perAdd));
            json.put("接种完成",jsonOther);
            return ResponseData.ok(json);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "查询统计数据--最近一个月完成接种情况",notes = "最近一个月完成接种情况")
    @GetMapping("/four")
    public ResponseData<JSONArray> jsonFour(@RequestHeader(WebConstants.HEADER)String token){
        try {
            UserVo userVo = new UserVo(iAuthService.findByToken(token));
            String deptId = userVo.getDeptId();
            Date date = new Date();
            JSONArray jsonArray = new JSONArray();
            // 获取当月天数
            Integer day = Integer.parseInt(DateUtil.format(date,"dd"));
            for (int i=1; i<=day; ++i){
                QueryWrapper<Vaccination> query = getQuery(deptId,i,date);
                Integer count = vaccinationMapper.finishCount(query);
                JSONObject json = new JSONObject();
                json.put("day",i+"号");
                json.put("count",TcloudUtils.isEmpty(count)?0:count);
                jsonArray.add(json);
            }
            return ResponseData.ok(jsonArray);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }



    public QueryWrapper<Vaccination> getQuery(String deptId,Integer num,Date date){
        QueryWrapper<Vaccination> query = Wrappers.query();
        query.eq("v.dept_id",deptId);
        query.eq("v.deleted",0);
        String before = DateUtil.format(date,"yyyy-MM");
        String startTime = "";
        String endTime = "";
        if (num < 10 ){
            startTime = before +"-0"+num+" 00:00:01";
            endTime = before +"-0"+num+" 23:59:59";
        }else {
            startTime = before +"-"+num+" 00:00:01";
            endTime = before +"-"+num+" 23:59:59";
        }
        query.ge("v.time",startTime);
        query.le("v.time",endTime);
        return query;
    }
}
