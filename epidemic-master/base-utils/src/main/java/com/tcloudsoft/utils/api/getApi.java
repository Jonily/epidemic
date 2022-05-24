package com.tcloudsoft.utils.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class getApi {

    public static Map getLicense() {
        Map<String,Object> map = new HashMap<>();
        // 获取token
        String accessToken = Access_token.getAuth(1);
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        try {

//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            //读取本地图片输入流
            FileInputStream inputStream = new FileInputStream("D:/11111.jpg");
            String base = Base64.encodeBase64String(IOUtils.toByteArray(inputStream));
//            String imgStr = Base64Util.encode(base);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(base, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
//            String accessToken = "#####调用鉴权接口获取的token#####";
            String result = HttpUtil.post(otherHost, accessToken, params);
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray words_result = (JSONArray) jsonObject.get("words_result");
            JSONObject o = (JSONObject)words_result.get(0);
            map.put("lincens",o.get("words"));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Map getIdentity() {
        Map<String,Object> map = new HashMap<>();
        // 获取token
        String accessToken = Access_token.getAuth(1);
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            //读取本地图片输入流
            FileInputStream inputStream = new FileInputStream("D:/111111.jpg");
            String base = Base64.encodeBase64String(IOUtils.toByteArray(inputStream));
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(base, "UTF-8");
//            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(base, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String result = HttpUtil.post(otherHost, accessToken, params);
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            JSONArray words_result = (JSONArray) jsonObject.get("words_result");
//            JSONObject o = (JSONObject)words_result.get(0);
            map.put("isCar",result);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static void main(String[] args) {
        System.out.println(getLicense());
    }
}
