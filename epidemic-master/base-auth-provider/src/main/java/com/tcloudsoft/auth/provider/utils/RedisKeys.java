package com.tcloudsoft.auth.provider.utils;

/**
 * redis常量类【key，expire time】 key:统一格式为【键类型:字段1=%s;字段2=%s...】，并使用String.format()生成完整key expire
 * time:单位统一为秒
 * @author liuwei
 */
public class RedisKeys {

  //用户token值对应key
  public static final String USER_TOKEN_KEY = "%s-UID-%s-TOKEN";
  public static final long USER_TOKEN_EXPIRE = 60 * 60 * 24 * 30;// 30天

  //用户失败次数对应key
  public static final String USER_LOGIN_FAILED_KEY = "UID-%s-FAILED";

  /* 用户信息 */
  public static final String USER_INFO = "user_info:%s:user";

  /* 短信验证码 */
  public static final String SMS_CODE = "sms_code:type=%s:phone=%s";
  public static final long SMS_CODE_EXPIRE = 60 * 30;// 30分钟

  /* 当天发送短信验证码次数 */
  public static final String SMS_CODE_DAY_COUNT = "sms_code_day_count:type=%s:phone=%s";


  public static String formatKey(String key, Object... params) {
    return String.format(key, params);
  }

}
