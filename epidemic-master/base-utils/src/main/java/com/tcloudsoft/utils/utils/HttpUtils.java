package com.tcloudsoft.utils.utils;

import com.tcloudsoft.utils.TcloudUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class HttpUtils {


    /**
     * 获取客户端真实IP
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
         try {
             String ip = request.getHeader("X-Real-IP");
             if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 String str = request.getHeader("X-Forwarded-For");
                 if(TcloudUtils.isNotEmpty(str)){
                     if(str.contains(",")){
                         ip = str.substring(0,str.indexOf(","));
                     }else {
                         ip = str;
                     }
                 }
             }
             if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("WL-Proxy-Client-IP");
             }
             if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("HTTP_CLIENT_IP");
             }
             if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("HTTP_X_FORWARDED_FOR");
             }
             if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getRemoteAddr();
             }
             return ip;
         }catch (Exception e){
             log.error("获取IP出错",e);
             return "";
         }
     }
}
