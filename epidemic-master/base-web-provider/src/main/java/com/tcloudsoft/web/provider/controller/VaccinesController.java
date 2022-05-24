package com.tcloudsoft.web.provider.controller;


import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.web.provider.model.Vaccines;
import com.tcloudsoft.web.provider.service.VaccinesService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  疫苗品牌 前端控制器
 * </p>
 *
 * @since 2021-12-06
 */
@Slf4j
@RestController
@RequestMapping("/vaccines")
public class VaccinesController {

    @Resource
    VaccinesService vaccinesService;

    @ApiOperation(value = "查询疫苗品牌列表",notes = "web端查询疫苗品牌列表")
    @GetMapping("list")
    public ResponseData<List<Vaccines>> get(){
        try {
            return ResponseData.ok(vaccinesService.list());
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @ApiOperation(value = "根据疫苗品牌选择针数",notes = "web端根据疫苗品牌选择针数")
    @GetMapping("/getNum")
    public ResponseData<List<Integer>> getNum(@RequestParam String id){
        try {
            Vaccines vaccines = vaccinesService.getById(id);
            if (TcloudUtils.isEmpty(vaccines)){
                return ResponseData.fail("疫苗品牌不存在！");
            }
            if (TcloudUtils.isEmpty(vaccines.getNumber()) || 0 == vaccines.getNumber()){
                return ResponseData.fail("该疫苗找不到针数信息！");
            }
            Integer num = vaccines.getNumber();
            List<Integer> nums = new ArrayList<>();
            for (int i = 0 ;i <num ; i++){
                nums.add(i+1);
            }
            return ResponseData.ok(nums);
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail("查询失败");
        }
    }

}
