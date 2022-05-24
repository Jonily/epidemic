package com.tcloudsoft.utils;

import java.security.MessageDigest;

/**
 * 云平台密码加密类，这里主要采用的md5的加密方式
 */
public class MD5PasswordEncoder {

  public String encode(CharSequence rawPassword) {
    if (rawPassword == null || "".equals(rawPassword)) {
      return null;
    }
    // 用于加密的字符
    char md5String[] =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    try {
      // 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
      byte[] btInput = rawPassword.toString().getBytes();
      // 获得指定摘要算法的 MessageDigest对象，此处为MD5
      // MessageDigest类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
      // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      // MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
      mdInst.update(btInput);
      // MD5 Message Digest from SUN, <in progress>
      // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
      byte[] md = mdInst.digest();

      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) { // i = 0
        byte byte0 = md[i]; // 95
        str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
        str[k++] = md5String[byte0 & 0xf]; // F
      }
      // 返回经过加密后的字符串
      return new String(str);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    if (encodedPassword == null || encodedPassword.length() == 0) {
      return false;
    }
    if (rawPassword == null || rawPassword.length() == 0) {
      return false;
    }
    String rawEncode = this.encode(rawPassword);
    if (rawEncode.equals(encodedPassword)) {
      return true;
    }
    return false;
  }

  public static String encode(String password) {
    if (password == null || "".equals(password)) {
      return null;
    }
    // 用于加密的字符
    char md5String[] =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    try {
      // 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
      byte[] btInput = password.toString().getBytes();
      // 获得指定摘要算法的 MessageDigest对象，此处为MD5
      // MessageDigest类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
      // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      // MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
      mdInst.update(btInput);
      // MD5 Message Digest from SUN, <in progress>
      // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
      byte[] md = mdInst.digest();

      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) { // i = 0
        byte byte0 = md[i]; // 95
        str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
        str[k++] = md5String[byte0 & 0xf]; // F
      }
      // 返回经过加密后的字符串
      return new String(str);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
