package com.tcloudsoft.auth.provider.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.model.Region;
import com.tcloudsoft.auth.provider.service.RegionService;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @description: 全国区域接口
 * @author zhuolin.Huang
 * @date 2022/5/24 19:21
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/region")
public class RegionController {

    @Resource
    RegionService regionService;

    /**
     * 查询区域数据
     *
     * @param id
     * @return tangjia
     */
    @GetMapping("/select")
    @ApiOperation(value = "下拉数据源", notes = "传入父节点")
    public ResponseData<List<Region>> select(
            @RequestParam(required = false, defaultValue = "1") String id) {
        try {
            List<Region> list =
                    regionService.list(Wrappers.<Region>query().lambda().eq(Region::getParentId, id));
            return ResponseData.ok(list);
        } catch (Exception e) {
            log.error("区域数据加载失败", e);
            return ResponseData.fail(ResponseCodeEnum.C30010);
        }
    }

    @GetMapping("/getName")
    public ResponseData<Object> getByCode(@RequestParam String code) {
        try {
            String name = regionService.getCodeByName(code);
            return ResponseData.ok(name);
        } catch (Exception e) {
            log.error("查询区域名称失败", e);
            return ResponseData.fail(ResponseCodeEnum.C30010);
        }
    }

}
