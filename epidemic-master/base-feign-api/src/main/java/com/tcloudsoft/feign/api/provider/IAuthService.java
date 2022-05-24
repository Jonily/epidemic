package com.tcloudsoft.feign.api.provider;

import com.tcloudsoft.utils.common.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.tcloudsoft.feign.api.provider.fallback.AuthServiceFallback;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.response.ResponseData;


/**
 * base-auth-provider服务对外暴露的相关接口信息
 *  供其他服务进行调用
 */
@FeignClient(value = "base-auth-provider", fallback = AuthServiceFallback.class)
public interface IAuthService {

  /**
   * 验证token是否有效
   * @param token
   * @return
   */
  @PostMapping(value = "/manage/checkToken")
  ResponseData<Boolean> checkToken(@RequestHeader(WebConstants.HEADER) String token);

  @GetMapping(value = "/manage/get")
  ResponseData<UserVo> findByToken(@RequestHeader(WebConstants.HEADER)String token);


}
