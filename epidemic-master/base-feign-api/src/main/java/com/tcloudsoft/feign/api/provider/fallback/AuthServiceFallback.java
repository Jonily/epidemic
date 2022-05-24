package com.tcloudsoft.feign.api.provider.fallback;

import com.tcloudsoft.utils.common.UserVo;
import org.springframework.stereotype.Component;
import com.tcloudsoft.feign.api.provider.IAuthService;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthServiceFallback implements IAuthService {

  @Override
  public ResponseData<Boolean> checkToken(String token) {
    log.error("调用{}异常{}", "checkToken", token);
    return ResponseData.fail(ResponseCodeEnum.C00002);
  }

  @Override
  public ResponseData<UserVo> findByToken(String token) {
    log.error("获取用户信息失败",token);
    return ResponseData.fail(ResponseCodeEnum.C00002);
  }
}
