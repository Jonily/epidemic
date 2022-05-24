package com.tcloudsoft.web.provider.controller;


import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import com.tcloudsoft.web.provider.model.VisitType;
import com.tcloudsoft.web.provider.service.VisitTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2021-12-06
 */
@Slf4j
@RestController
@RequestMapping("/visitType")
public class VisitTypeController {

    @Resource
    VisitTypeService visitTypeService;

    @GetMapping("/list")
    public ResponseData<List<VisitType>> list(){
        try {
            return ResponseData.ok(visitTypeService.list());
        }catch (Exception e){
            log.error("查询失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }

    }

}
