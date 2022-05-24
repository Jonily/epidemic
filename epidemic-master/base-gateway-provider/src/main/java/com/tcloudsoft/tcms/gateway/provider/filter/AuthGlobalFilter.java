package com.tcloudsoft.tcms.gateway.provider.filter;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.alibaba.fastjson.JSONObject;
import com.tcloudsoft.feign.api.provider.IAuthService;
import com.tcloudsoft.tcms.gateway.provider.config.ListConfig;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
  @Autowired
  IAuthService authService;
  @Autowired
  private ListConfig listConfig;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String requestUri = request.getPath().pathWithinApplication().value();
    if (TcloudUtils.isNotEmpty(listConfig.getList())) {
      for (String l : listConfig.getList()) {
        if (requestUri.contains(l)) {
           return chain.filter(exchange);
        }
      }
    }
    // 排除ws的请求拦截
    if (requestUri.indexOf("/ws/") > -1) {
      return chain.filter(exchange);
    }

    // 从header中获取token
    String token = request.getHeaders().getFirst(WebConstants.HEADER);
    // 如果从header获取token为空，则从参数中获取token
    if (TcloudUtils.isEmpty(token)) {
      token = String.valueOf(request.getQueryParams().getFirst(WebConstants.HEADER));
    }
    if (TcloudUtils.isNotEmpty(token)) {
      // 验证token
      ResponseData<Boolean> result = authService.checkToken(token);
      if (result.isSuccess() && result.getData()) {
        return chain.filter(exchange);
      } else {
        // 认证失败，返回失败信息
        return this.writeDataToResponse(exchange, result, HttpStatus.UNAUTHORIZED);
      }
    }
    String openId = request.getHeaders().getFirst(WebConstants.OPENID);
    if (TcloudUtils.isNotEmpty(openId)){
      return chain.filter(exchange);
    }
    return this.writeDataToResponse(exchange, ResponseData.fail(ResponseCodeEnum.GATEWAY00001),
        HttpStatus.UNAUTHORIZED);
  }

  private Mono<Void> writeDataToResponse(ServerWebExchange exchange, ResponseData<?> body,
      HttpStatus status) {
    exchange.getResponse().setStatusCode(status);
    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    byte[] bytes = JSONObject.toJSONString(body).getBytes(StandardCharsets.UTF_8);
    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
    return exchange.getResponse().writeWith(Flux.just(buffer));
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
