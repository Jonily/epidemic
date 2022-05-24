package com.tcloudsoft.tcms.gateway.provider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * 判断请求路径上是否以配置的servlet.context-path开头
 * 如果不是，直接返回502
 */
@Configuration
public class ApiPrefixConfig {

  @Value("${server.servlet.context-path}")
  private String prefix;

  @Bean
  @Order(-1)
  public WebFilter apiPrefixFilter() {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      String path = request.getURI().getRawPath();
      ServerWebExchangeUtils.addOriginalRequestUrl(exchange, request.getURI());
      if(path.contains(".ico")){//放开请求路径包含.ico的相关请求
        return chain.filter(exchange);
      }else if (!path.contains(prefix)) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_GATEWAY);
        DataBuffer buffer = exchange.getResponse().bufferFactory()
            .wrap(HttpStatus.BAD_GATEWAY.getReasonPhrase().getBytes());
        return response.writeWith(Mono.just(buffer));
      }
      String newPath = path.replaceFirst(prefix, "");
      ServerHttpRequest newRequest = request.mutate().path(newPath).build();
      exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR,
          newRequest.getURI());
      return chain.filter(exchange.mutate().request(newRequest).build());
    };
  }
}
