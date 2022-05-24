//package com.tcloudsoft.auth.provider.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import com.tcloudsoft.auth.provider.common.CommonCache;
//
//@Component
//public class MyCommandLineRunner implements ApplicationRunner {
//
//  @Value("${customConfig.sms.accessKeyId}")
//  private String smsAccessKeyId;
//
//  @Value("${customConfig.sms.accessSecret}")
//  private String smsAccessSecret;
//
//  @Value("${customConfig.sms.regionId}")
//  private String smsRegionId;
//
//  @Value("${customConfig.sms.version}")
//  private String smsVersion;
//
//  @Value("${customConfig.sms.domain}")
//  private String smsDomain;
//
//  @Value("${customConfig.sms.signName}")
//  private String smsSignName;
//
//
//  @Override
//  public void run(ApplicationArguments args) {
//    try {
//      CommonCache.put("smsAccessKeyId", smsAccessKeyId);
//      CommonCache.put("smsAccessSecret", smsAccessSecret);
//      CommonCache.put("smsRegionId", smsRegionId);
//      CommonCache.put("smsVersion", smsVersion);
//      CommonCache.put("smsDomain", smsDomain);
//      CommonCache.put("smsSignName", smsSignName);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//}
