package com.tcloudsoft.auth.provider.controller;


import com.tcloudsoft.auth.provider.service.DeptInfoService;
import com.tcloudsoft.auth.provider.service.DeptService;
import com.tcloudsoft.utils.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @description: 社区接口
 * @author zhuolin.Huang
 * @date 2022/5/24 19:18
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/deptInfo")
public class DeptInfoController {

    @Resource
    DeptInfoService deptInfoService;

    @GetMapping("/get")
    public ResponseData<Object> list(){
        try {
            // 返回所有社区
            return ResponseData.ok(deptInfoService.list());
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail("查询失败");
        }
    }
}
