package com.tcloudsoft.tcms.gateway.provider;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 网关  程序入口
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.tcloudsoft.feign.api.provider"})
public class GatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    List<MediaType> supportedMediaTypes = new ArrayList<>();
    supportedMediaTypes.add(MediaType.APPLICATION_JSON);
    supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
    supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
    supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
    supportedMediaTypes.add(MediaType.APPLICATION_PDF);
    supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
    supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
    supportedMediaTypes.add(MediaType.APPLICATION_XML);
    supportedMediaTypes.add(MediaType.IMAGE_GIF);
    supportedMediaTypes.add(MediaType.IMAGE_JPEG);
    supportedMediaTypes.add(MediaType.IMAGE_PNG);
    supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
    supportedMediaTypes.add(MediaType.TEXT_HTML);
    supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
    supportedMediaTypes.add(MediaType.TEXT_PLAIN);
    supportedMediaTypes.add(MediaType.TEXT_XML);
    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    fastConverter.setSupportedMediaTypes(supportedMediaTypes);
    return new HttpMessageConverters(fastConverter);
  }


  // @Bean
  // public CorsWebFilter corsFilter() {
  // CorsConfiguration config = new CorsConfiguration();
  // config.setAllowCredentials(true); // 允许cookies跨域
  // config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
  // config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
  // config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
  // config.addAllowedMethod("*");// 允许提交请求的方法类型，*表示全部允许
  // org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source =
  // new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource(
  // new PathPatternParser());
  // source.registerCorsConfiguration("/**", config);
  //
  // return new CorsWebFilter(source);
  // }
}
