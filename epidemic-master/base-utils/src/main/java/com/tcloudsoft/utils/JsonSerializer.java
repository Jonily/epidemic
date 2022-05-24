package com.tcloudsoft.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * 
 * @project Fusion-Common
 * @package com.fusionzoom.common.utils
 * @class JsonSerializer.java
 * @date 2015年11月13日 下午12:28:52
 * @description
 */
public class JsonSerializer {

  private static List<String> NO_SYMBOL_TYPES = new ArrayList<>();

  static {
    NO_SYMBOL_TYPES.add("int");
    NO_SYMBOL_TYPES.add("java.lang.Integer");
    NO_SYMBOL_TYPES.add("long");
    NO_SYMBOL_TYPES.add("java.lang.Long");
    NO_SYMBOL_TYPES.add("double");
    NO_SYMBOL_TYPES.add("java.lang.Double");
    NO_SYMBOL_TYPES.add("boolean");
    NO_SYMBOL_TYPES.add("java.lang.Boolean");
  }

  public static ObjectMapper getMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }

  public static <T> T deserialize(Class<T> clazz, String serialJson) {
    if (serialJson == null || serialJson.length() == 0) {
      return null;
    }
    try {
      ObjectMapper mapper = getMapper();
      return mapper.readValue(serialJson, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * @param <T>
   * @param containClazz
   * @param genClazz
   * @param serialJson
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static <T> List<T> deserialize(Class<? extends List> containClazz, Class<T> genClazz,
      String serialJson) {
    if (serialJson == null || serialJson.length() == 0) {
      return null;
    }
    try {
      ObjectMapper mapper = getMapper();
      JavaType type = TypeFactory.defaultInstance().constructCollectionType(containClazz, genClazz);
      return mapper.readValue(serialJson, type);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // public static void main(String[] args) {
  // List<String> strs = new ArrayList<String>();
  // strs.add("/party/app/index");
  // String json = JsonSerializer.serialize(strs);
  // System.out.println(json);
  //
  // System.out.println(JsonSerializer.deserialize(ArrayList.class, String.class, json));
  // }

  /**
   * 
   * @param obj
   * @return
   */
  public static <T> String serializeForJs(T obj) {
    try {
      ObjectMapper mapper = getMapper();
      String str = mapper.writeValueAsString(obj);
      str = str.replace("'", "\\\'");
      str = str.replace("\\\"", "\\\'");
      return str;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> String serialize(T obj) {
    try {
      ObjectMapper mapper = getMapper();
      String str = mapper.writeValueAsString(obj);
      return str;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String getAlisaName(String fieldName) {
    StringBuffer sBuf = new StringBuffer();
    for (char c : fieldName.toCharArray()) {
      if (c >= 65 && c <= 90) {
        sBuf.append("_").append(String.valueOf(c).toLowerCase());
      } else {
        sBuf.append(c);
      }
    }
    return sBuf.toString();
  }

  public static String serializeObj(Object obj) {
    Class<Object> oldClazz = (Class<Object>) obj.getClass();
    Field[] fields = oldClazz.getDeclaredFields();
    StringBuffer serializeBuf = new StringBuffer();
    if (fields != null && fields.length > 0) {
      for (Field f : fields) {
        String fieldName = f.getName();
        // String getMethodName = "get"
        // + fieldName.substring(0, 1).toUpperCase()
        // + fieldName.substring(1);
        String getMethodName = "get";
        if (Character.isUpperCase(fieldName.charAt(1))) { // 判断第二个字符是否是大写
          getMethodName += fieldName;
        } else {
          getMethodName += fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
        if (fieldName.equals("serialVersionUID")) {
          continue;
        }
        fieldName = getAlisaName(fieldName);
        String typeName = f.getType().getCanonicalName();
        Object getVal = null;
        try {
          Method oldM = (Method) oldClazz.getMethod(getMethodName);
          if (oldM != null) {
            getVal = oldM.invoke(obj);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (NO_SYMBOL_TYPES.contains(typeName)) {
          if (getVal != null) {
            serializeBuf.append("\"").append(fieldName).append("\":").append(getVal).append(",");
          } else {
            serializeBuf.append("\"").append(fieldName).append("\":null,");
          }
        } else if (typeName.equals("java.util.Date")) {
          if (getVal != null) {
            serializeBuf.append("\"").append(fieldName).append("\":")
                .append(((Date) getVal).getTime()).append(",");
          } else {
            serializeBuf.append("\"").append(fieldName).append("\":null,");
          }
        } else {
          if (getVal != null) {
            serializeBuf.append("\"").append(fieldName).append("\":\"").append(getVal.toString())
                .append("\",");
          } else {
            serializeBuf.append("\"").append(fieldName).append("\":null,");
          }
        }
      }
    }
    String json = "";
    if (serializeBuf.length() > 0) {
      json = serializeBuf.substring(0, serializeBuf.length() - 1);
    }
    json = "{" + json + "}";
    return json;
  }

  private static String getDeAlisaName(String fieldName) {
    StringBuffer sBuf = new StringBuffer();
    boolean isNextUpr = false;
    for (char c : fieldName.toCharArray()) {
      if (c == '_') {
        isNextUpr = true;
        continue;
      }
      if (isNextUpr) {
        sBuf.append(String.valueOf(c).toUpperCase());
        isNextUpr = false;
      } else {
        sBuf.append(c);
      }
    }
    return sBuf.toString();
  }

  public static Object deSerializeObj(String json, Object obj) {
    Pattern pattern = Pattern.compile("\"(.+?)\":");
    Matcher matcher = pattern.matcher(json);
    StringBuffer i18nBuf = new StringBuffer();
    while (matcher.find()) {
      String i18nText = matcher.group();
      i18nText = i18nText.substring(1, i18nText.length() - 2);
      String i18nValue = "\"" + getDeAlisaName(i18nText) + "\"";
      matcher.appendReplacement(i18nBuf, i18nValue);
    }
    matcher.appendTail(i18nBuf);
    return deserialize(obj.getClass(), i18nBuf.toString());
  }


  public static String serializeObjFilterTheNull(Object obj) {
    Class<Object> oldClazz = (Class<Object>) obj.getClass();
    Field[] fields = oldClazz.getDeclaredFields();
    StringBuffer serializeBuf = new StringBuffer();
    if (fields != null && fields.length > 0) {
      for (Field f : fields) {
        String fieldName = f.getName();
        String getMethodName = "get";
        if (Character.isUpperCase(fieldName.charAt(1))) { // 判断第二个字符是否是大写
          getMethodName += fieldName;
        } else {
          getMethodName += fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
        if (fieldName.equals("serialVersionUID")) {
          continue;
        }
        fieldName = getAlisaName(fieldName);
        String typeName = f.getType().getCanonicalName();
        Object getVal = null;
        try {
          Method oldM = (Method) oldClazz.getMethod(getMethodName);
          if (oldM != null) {
            getVal = oldM.invoke(obj);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (NO_SYMBOL_TYPES.contains(typeName)) {
          if (getVal != null) {
            serializeBuf.append("\"").append(fieldName).append("\":").append(getVal).append(",");
          } else {
            continue;
          }
        } else if (typeName.equals("java.util.Date")) {
          if (getVal != null) {
            serializeBuf.append("\"").append(fieldName).append("\":")
                .append(((Date) getVal).getTime()).append(",");
          } else {
            continue;
          }
        } else {
          if (getVal != null) {
            serializeBuf.append("\"").append(fieldName).append("\":\"").append(getVal.toString())
                .append("\",");
          } else {
            continue;
          }
        }
      }
    }
    String json = "";
    if (serializeBuf.length() > 0) {
      json = serializeBuf.substring(0, serializeBuf.length() - 1);
    }
    json = "{" + json + "}";
    return json;
  }
  // public static void main(String[] args) {
  // TradeOrder tradeOrder = new TradeOrder();
  // tradeOrder.setId(1l);
  // tradeOrder.setCreateTime(new Date());
  // tradeOrder.setPaidTime(new Date());
  // tradeOrder.setAmountExp(true);
  // tradeOrder.setPrimitiveSaleIncome(11d);
  // Object o = deSerializeObj(serializeObj(tradeOrder), tradeOrder);
  // System.out.println(o);
  // System.out.println((int)'a'+"::"+(int)'z');
  // String fieldName = "aTa";
  // String getMethodName = "get";
  // char chr = fieldName.charAt(1);
  // if(Character.isUpperCase(chr)){
  // getMethodName += fieldName;
  // }else {
  // getMethodName += fieldName.substring(0, 1).toUpperCase()
  // + fieldName.substring(1);
  // }
  // System.out.println(getMethodName);
  // }
}
