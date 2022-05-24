package com.tcloudsoft.auth.provider.controller;


import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
import com.tcloudsoft.auth.provider.service.ManageService;
import com.tcloudsoft.auth.provider.vo.AuthQuery;
import com.tcloudsoft.auth.provider.vo.AuthResult;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.common.UserVo;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @description: 管理员接口
 * @author zhuolin.Huang
 * @date 2022/5/24 19:19
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manage")
public class ManageController {

    @Resource
    ManageService manageService;

    @ApiOperation(value = "用户登录验证",notes = "web端管理员登录验证")
    @PostMapping("/login")
    public ResponseData<Object> auth (@RequestBody AuthQuery query){
        try {
            if (TcloudUtils.isEmpty(query.getUsername()) || StringUtils.isEmpty(query.getPassword())) {
                return ResponseData.fail(ResponseCodeEnum.C00001);
            }
            AuthResult authResult = manageService.auth(query);
            return ResponseData.ok(authResult);
        }catch (IllegalArgumentException e){
            log.error("登录失败",e);
            return ResponseData.fail(e.getMessage());
        }catch (Exception e){
            log.error("登录失败",e);
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出当前登录用户")
    public ResponseData<?> logout(@ApiParam(value = "token",
            required = true) @RequestHeader(WebConstants.HEADER) String token) {
        try {
            manageService.logout(token);
            return ResponseData.ok("");
        } catch (TcmsAuthException e) {
            return ResponseData.fail(e);
        } catch (Exception e) {
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }

    @PostMapping("/checkToken")
    @ApiOperation(value = "检查token是否有效", notes = "检查token是否有效")
    public ResponseData<Boolean> check(@ApiParam(value = "token",
            required = true) @RequestHeader(WebConstants.HEADER) String token) {
        try {
            manageService.check(token);
            return ResponseData.ok(true);
        } catch (TcmsAuthException e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @GetMapping("/get")
    @ApiOperation(value = "根据token获取用户信息")
    public ResponseData<UserVo> getByToken(@RequestHeader(WebConstants.HEADER) String token) {
        try {
            // 从redis获取当前用户信息
            AuthResult authResult = manageService.findByToken(token);
            return ResponseData.ok(authResult.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseData.fail(e.getMessage());
        }catch (Exception e){
            return ResponseData.fail(ResponseCodeEnum.C00002);
        }
    }
}
