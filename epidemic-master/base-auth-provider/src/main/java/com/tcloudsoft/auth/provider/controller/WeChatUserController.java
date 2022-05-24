//package com.tcloudsoft.auth.provider.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.tcloudsoft.auth.provider.model.Open;
//import com.tcloudsoft.auth.provider.model.Person;
//import com.tcloudsoft.auth.provider.service.OpenService;
//import com.tcloudsoft.auth.provider.service.PersonService;
//import com.tcloudsoft.auth.provider.utils.*;
//import com.tcloudsoft.utils.TcloudUtils;
//import com.tcloudsoft.utils.constants.WebConstants;
//import com.tcloudsoft.utils.ex.ResponseCodeEnum;
//import com.tcloudsoft.utils.response.ResponseData;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.util.PropertiesUtil;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
//
///**
// * <p>
// * 前端控制器
// * </p>
// * 微信相关操作控制器
// * @author longgd
// * @since 2020-11-10
// */
//@RestController
//@RequestMapping("/weChatUser")
//@Slf4j
//public class WeChatUserController {
//
//  /**
//   * 微信公共号接口
//   */
//  private static final String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
//
//  /**
//   * 小程序接口
//   */
//  private static final String accessTokenUrl2 = "https://api.weixin.qq.com/sns/jscode2session";
//
//  //网页授权获取用户信息url
//  private static final String getUserInfo = "https://api.weixin.qq.com/sns/userinfo";
//
//  //利用普通的access_token获取用户信息url
//  private static final String getUserInfo2 = "https://api.weixin.qq.com/cgi-bin/user/info";
//
//  //获取access_token
//  private static final String accessToken = "https://api.weixin.qq.com/cgi-bin";
//
//  //农户
//  private static final String index1 = "/pages/grainGrower/information/register";
//
//  //经纪人
//  private static final String index2 = "/pages/brokerNews/brokerNews";
//
//  //选择登录类型界面
//  private static final String index3= "/pages/home/home";
//
//  @Value("${wx.appid}")
//  private String appid;
//  @Value("${wx.secret}")
//  private String secret;
//  @Resource
//  OpenService openService;
//  @Resource
//  PersonService personService;
//
//
//  /**
//   * 微信公众号权限认证
//   *   （通过code + appId + secret的方式）
//   * @param code
//   * @param appid
//   * @param secret
//   * @return
//   * @throws Exception
//   */
//  public JSONObject openIdByCode(String code, String appid, String secret) throws Exception {
//    JSONObject jsonObject = new JSONObject();
//    String url = accessTokenUrl + "?appid=" + appid + "&secret=" + secret + "&code=" + code
//            + "&grant_type=authorization_code";
//    String result = HttpClientUtil.sendGetRequest(url, null);
//    jsonObject = JSON.parseObject(result);
//    return jsonObject;
//  }
//
//
//
//  /**
//   * 微信公众号授权接口
//   * @param code
//   * @param openId
//   * @return
//   */
//  @GetMapping("/auth")
//  public ResponseData<Object> auth(@RequestParam(required = false, value = "code") String code,
//                                   @RequestHeader(WebConstants.OPENID) String openId) {
//    try {
//      log.info("----------进入授权接口：-----接收到：-----code:{}",code);
//      log.info("----------进入授权接口：-------接收到：----openId:{}"+openId);
////      Open open = new Open();//种粮人
////      //如果前端传了openId,就查询openId对应的用户信息
////      if (TcloudUtils.isNotEmpty(openId) && !"null".equals(openId)) {
////        //查询该openId对应的数据（具体业务代码）
////        open = openService.getByOpenId(openId);
////      }
//
//      String errorCode = "";
//      Map<String,Object> map = new HashMap<>();
////        if (TcloudUtils.isEmpty(openId) || "null".equals(openId)) {
////          JSONObject accessToken1 = openIdByCode(code, appid, secret);
////          openId = accessToken1.getString("openid");
////          if(TcloudUtils.isEmpty(openId)){//如果获取的openId为空
////            errorCode="40029";
////          }
////          log.info("----------------：调用接口授权，获取到的openId为{}",openId);
////        }
////        if(openId==null||openId.equals("")){
////          return ResponseData.fail("500", "获取微信信息失败！");
////        }
//      map.put("openId", openId);
//      Open open = openService.getByOpenId(openId);
//      if (open==null){
//        open=new Open();
//        open.setOpenId(openId);
//        openService.save(open);
//      }
////      if (TcloudUtils.isEmpty(open) && TcloudUtils.isEmpty(open.getId())) {
////        openId = "null";
////        //调用权限认证接口 获取openID  (微信公众号)
////        JSONObject accessToken1 = openIdByCode(code, appid, secret);
////        if (TcloudUtils.isEmpty(openId) || "null".equals(openId)) {
////          openId = accessToken1.getString("openid");
////          if(TcloudUtils.isEmpty(openId)){//如果获取的openId为空
////            errorCode="40029";
////          }
////          log.info("----------------：调用接口授权，获取到的openId为{}",openId);
////          map.put("openId", openId);
////        }
////      }
//      Person person = new Person();
//      if (TcloudUtils.isNotEmpty(open)){
//        List<Person> list = personService.findByPhone(open.getIdCard());
//        if (TcloudUtils.isNotEmpty(list) && list.size() >0){
//          person = list.get(0);
//        }
//      }
//      //具体的返回的业务代码
//      map.put("error",errorCode);
//      map.put("person",person);
//      log.info("授权结束------返回的jo对象为:{}",map);
//      return ResponseData.ok(map);
//    } catch (Exception e) {
//      log.error("获取微信信息失败！", e);
//      return ResponseData.fail("500", "获取微信信息失败！");
//    }
//  }
//
//
//}


package com.tcloudsoft.auth.provider.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcloudsoft.auth.provider.model.Open;
import com.tcloudsoft.auth.provider.model.Person;
import com.tcloudsoft.auth.provider.service.OpenService;
import com.tcloudsoft.auth.provider.service.PersonService;
import com.tcloudsoft.auth.provider.utils.*;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.constants.WebConstants;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/**
 * @description: 微信用户接口
 * @author zhuolin.Huang
 * @date 2022/5/24 19:21
 * @version 1.0
 */
@RestController
@RequestMapping("/weChatUser")
@Slf4j
public class WeChatUserController {

  /**
   * 微信公共号接口
   */
  private static final String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
  /**
   * 小程序接口
   */
  private static final String accessTokenUrl2 = "https://api.weixin.qq.com/sns/jscode2session";

  //网页授权获取用户信息url
  private static final String getUserInfo = "https://api.weixin.qq.com/sns/userinfo";

  //利用普通的access_token获取用户信息url
  private static final String getUserInfo2 = "https://api.weixin.qq.com/cgi-bin/user/info";

  //获取access_token
  private static final String accessToken = "https://api.weixin.qq.com/cgi-bin";

  //农户
  private static final String index1 = "/pages/grainGrower/information/register";

  //经纪人
  private static final String index2 = "/pages/brokerNews/brokerNews";

  //选择登录类型界面
  private static final String index3= "/pages/home/home";

  @Value("${wx.appid}")
  private String appid;
  @Value("${wx.secret}")
  private String secret;
  @Resource
  OpenService openService;
  @Resource
  PersonService personService;


  /**
   * 微信公众号权限认证
   *   （通过code + appId + secret的方式）
   * @param code
   * @param appid
   * @param secret
   * @return
   * @throws Exception
   */
  public JSONObject openIdByCode(String code, String appid, String secret) throws Exception {
    JSONObject jsonObject = new JSONObject();
    String url = accessTokenUrl + "?appid=" + appid + "&secret=" + secret + "&code=" + code
            + "&grant_type=authorization_code";
    String result = HttpClientUtil.sendGetRequest(url, null);
    jsonObject = JSON.parseObject(result);
    return jsonObject;
  }



  /**
   * 微信公众号授权接口
   * @param code
   * @param openId
   * @return
   */
  @GetMapping("/auth")
  public ResponseData<Object> auth(@RequestParam(required = false, value = "code") String code,
                                   @RequestHeader(WebConstants.OPENID) String openId) {
    try {
      log.info("----------进入授权接口：-----接收到：-----code:{}",code);
      log.info("----------进入授权接口：-------接收到：----openId:{}"+openId);
      Map<String,Object> map = new HashMap<>();
      map.put("openId", openId);
      Open open = null;//种粮人
      //如果前端传了openId,就查询openId对应的用户信息
      if (TcloudUtils.isNotEmpty(openId) && !"null".equals(openId)) {
        //查询该openId对应的数据（具体业务代码）
        open = openService.getByOpenId(openId);
      }
      String errorCode = "";
      if (TcloudUtils.isEmpty(open)) {
        openId = "null";
        //调用权限认证接口 获取openID  (微信公众号)
        JSONObject accessToken1 = openIdByCode(code, appid, secret);
        if (TcloudUtils.isEmpty(openId) || "null".equals(openId)) {
//                  openId = "omsjnwvY2eouG83uO7s5SQengdEY";
          openId = accessToken1.getString("openid");
          if(TcloudUtils.isEmpty(openId)){//如果获取的openId为空
            errorCode="40029";
          }
          log.info("----------------：调用接口授权，获取到的openId为{}",openId);
          map.put("openId", openId);
        }
      }
      Person person = new Person();
      if (TcloudUtils.isNotEmpty(open)){
        List<Person> list = personService.findByPhone(open.getIdCard());
        if (TcloudUtils.isNotEmpty(list) && list.size() >0){
          person = list.get(0);
        }
      }
      //具体的返回的业务代码
      map.put("error",errorCode);
      map.put("person",person);
      log.info("授权结束------返回的jo对象为:{}",map);
      return ResponseData.ok(map);
    } catch (Exception e) {
      log.error("获取微信信息失败！", e);
      return ResponseData.fail("500", "获取微信信息失败！");
    }
  }


}



