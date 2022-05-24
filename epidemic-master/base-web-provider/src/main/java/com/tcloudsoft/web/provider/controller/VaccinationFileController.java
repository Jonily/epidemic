package com.tcloudsoft.web.provider.controller;


import com.tcloudsoft.utils.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2021-12-06
 */
@Slf4j
@RestController
@RequestMapping("/vaccinationFile")
public class VaccinationFileController {


    @ApiOperation(value = "文件上传",notes = "app端文件上传")
    @PostMapping("/upload")
    public ResponseData<Object> upload(){
        try {
            return null;
        }catch (Exception e){
            log.error("上传失败",e);
            return ResponseData.fail("文件上传失败");
        }
    }

}
