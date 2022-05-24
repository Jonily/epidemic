package com.tcloudsoft.utils;

import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcloudsoft.utils.common.UserInfo;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.ex.TcloudException;

public class TokenGenerator {
  public static String generator(UserInfo user) throws TcloudException {
    try {
      user.setCreated(new Date().getTime());
      String token = RC4Util.encrypt(JSON.toJSONString(user));
      return token;
    } catch (Exception e) {
      e.printStackTrace();
      ResponseCodeEnum rce = ResponseCodeEnum.C00020;
      throw new TcloudException(rce.name(), rce.getMessage());
    }

  }

  public static UserInfo parseToken(String token) throws TcloudException {
    try {
      String jstr = RC4Util.decrypt(token);
      return JSONObject.toJavaObject(JSONObject.parseObject(jstr), UserInfo.class);
    } catch (Exception e) {
      ResponseCodeEnum rce = ResponseCodeEnum.C00021;
      throw new TcloudException(rce.name(), rce.getMessage());
    }
  }

}
