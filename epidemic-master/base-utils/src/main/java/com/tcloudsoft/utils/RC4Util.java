package com.tcloudsoft.utils;

/**
 * 负责生成ACCESS_TOKEN||REFRESH_TOKEN令牌
 * 
 * @author witts
 * @project core-utils
 * @package cc.zeelan.framework.dense
 * @version 1.0
 * @message 林花谢了春红，太匆匆。无奈朝来寒雨，晚来风
 */
public class RC4Util {
  /** ACCESS_TOKEN的加密钥匙 **/
  public static final String ACCESSKEY =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCka+JcKsICz3vWll+oW8vPd8MwddLGOgvA9/HtjDrWVxCCJeXvgC/DKTf6AkEvIGhoa5674WZPeEvfocAes5DGXgbSrnvcWDnDaSrvw0ORl4gGazsSo2DiM8iFS5V/v6cAqti/+zlz1nnDSJN6BVgRFaKBYjEzin7xRtJJWvB+PwIDAQAB";


  /** 加密 **/
  public static String encrypt(String data) {
    if (data == null) {
      return null;
    }
    return toHexString(asString(encrypt_byte(data)));
  }

  private static String asString(byte[] buf) {
    StringBuffer strbuf = new StringBuffer(buf.length);
    for (int i = 0; i < buf.length; i++) {
      strbuf.append((char) buf[i]);
    }
    return strbuf.toString();
  }

  private static String toHexString(String s) {
    String str = "";
    for (int i = 0; i < s.length(); i++) {
      int ch = (int) s.charAt(i);
      String s4 = Integer.toHexString(ch & 0xFF);
      if (s4.length() == 1) {
        s4 = '0' + s4;
      }
      str = str + s4;
    }
    return str;// 0x表示十六进制
  }

  /** 解密 **/
  public static String decrypt(String data) {
    if (data == null) {
      return null;
    }
    return new String(rc4Base(HexString2Bytes(data), ACCESSKEY));
  }

  /** 加密字节码 **/
  public static byte[] encrypt_byte(String data) {
    if (data == null) {
      return null;
    }
    byte b_data[] = data.getBytes();
    return rc4Base(b_data, ACCESSKEY);
  }


  private static byte[] initKey(String aKey) {
    byte[] b_key = aKey.getBytes();
    byte state[] = new byte[256];

    for (int i = 0; i < 256; i++) {
      state[i] = (byte) i;
    }
    int index1 = 0;
    int index2 = 0;
    if (b_key == null || b_key.length == 0) {
      return null;
    }
    for (int i = 0; i < 256; i++) {
      index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
      byte tmp = state[i];
      state[i] = state[index2];
      state[index2] = tmp;
      index1 = (index1 + 1) % b_key.length;
    }
    return state;
  }


  private static byte[] HexString2Bytes(String src) {
    int size = src.length();
    byte[] ret = new byte[size / 2];
    byte[] tmp = src.getBytes();
    for (int i = 0; i < size / 2; i++) {
      ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
    }
    return ret;
  }

  private static byte uniteBytes(byte src0, byte src1) {
    char _b0 = (char) Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
    _b0 = (char) (_b0 << 4);
    char _b1 = (char) Byte.decode("0x" + new String(new byte[] {src1})).byteValue();
    byte ret = (byte) (_b0 ^ _b1);
    return ret;
  }

  private static byte[] rc4Base(byte[] input, String mKkey) {
    int x = 0;
    int y = 0;
    byte key[] = initKey(mKkey);
    int xorIndex;
    byte[] result = new byte[input.length];

    for (int i = 0; i < input.length; i++) {
      x = (x + 1) & 0xff;
      y = ((key[x] & 0xff) + y) & 0xff;
      byte tmp = key[x];
      key[x] = key[y];
      key[y] = tmp;
      xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
      result[i] = (byte) (input[i] ^ key[xorIndex]);
    }
    return result;
  }



  public static void main(String[] args) {
    String data = "hello!myfirends";
    StringBuffer sb = new StringBuffer("");
    for (int i = 0; i < 500; i++) {
      sb.append(data);
    }
    System.out.println(sb);
    System.out.println("加密结果 " + RC4Util.encrypt(sb.toString()));
    System.out.println("解密结果 " + RC4Util.decrypt(RC4Util.encrypt(sb.toString())));
  }
}
