//package com.tcloudsoft.auth.provider.filter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.tcloudsoft.auth.provider.service.ManageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import com.alibaba.fastjson.JSONObject;
//import com.tcloudsoft.auth.provider.config.ListConfig;
//import com.tcloudsoft.auth.provider.ex.TcmsAuthException;
//import com.tcloudsoft.utils.TcloudUtils;
//import com.tcloudsoft.utils.constants.WebConstants;
//import com.tcloudsoft.utils.ex.ResponseCodeEnum;
//import com.tcloudsoft.utils.response.ResponseData;
//
///**
// * 过滤器
// */
//@Component
//@WebFilter(urlPatterns = "/api/*", filterName = "authFilter")
//public class HttpBasicAuthorizeFilter implements Filter {
//  @Autowired
//  private ListConfig listConfig;
//
//  @Autowired
//  ManageService manageService;
//
//  public void init(FilterConfig filterConfig) throws ServletException {}
//
//  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//      throws IOException, ServletException {
//    System.out.println(listConfig.getList());
//    HttpServletRequest httpRequest = (HttpServletRequest) request;
//    HttpServletResponse httpResponse = (HttpServletResponse) response;
//    httpResponse.setCharacterEncoding("UTF-8");
//    httpResponse.setContentType("application/json; charset=utf-8");
//    String requestUri = httpRequest.getRequestURI();
//
//    if (TcloudUtils.isNotEmpty(listConfig.getList())) {
//      for (String l : listConfig.getList()) {
////        if (requestUri.contains(l)) {
////          chain.doFilter(httpRequest, response);
////          return;
////        }
//        chain.doFilter(httpRequest, response);
//        return;
//      }
//    }
//    String token = httpRequest.getHeader(WebConstants.HEADER);
//    if (TcloudUtils.isEmpty(token)) {
//      token = String.valueOf(httpRequest.getParameter(WebConstants.HEADER));
//    }
//
//    if (TcloudUtils.isNotEmpty(token)) {
//      // 验证token
//      try {
//        manageService.check(token);
//        chain.doFilter(httpRequest, response);
//        return;
//      } catch (TcmsAuthException e) {
//        PrintWriter print = httpResponse.getWriter();
//        httpResponse.setStatus(401);
//        print.write(JSONObject.toJSONString(ResponseData.fail(e)));
//      }
//    } else {
//      PrintWriter print = httpResponse.getWriter();
//      httpResponse.setStatus(401);
//      print.write(JSONObject.toJSONString(ResponseData.fail(ResponseCodeEnum.GATEWAY00001)));
//    }
//  }
//
//
//  public void destroy() {
//
//  }
//
//}
