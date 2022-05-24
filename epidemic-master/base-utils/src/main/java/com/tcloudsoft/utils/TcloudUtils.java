package com.tcloudsoft.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TcloudUtils {
  public static final char UNDERLINE = '_';

  /**
   * 
   * 判断对象是否Empty(null或元素为0)<br>
   * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
   * 
   * @param pObj 待检查对象
   * @return boolean 返回的布尔值
   */
  public static boolean isEmpty(Object pObj) {
    if (pObj == null) {
      return true;
    }

    if (pObj == "") {
      return true;
    }
    if (pObj instanceof String) {
      if (((String) pObj).length() == 0) {
        return true;
      }
    } else if (pObj instanceof Collection) {
      if (((Collection<?>) pObj).size() == 0) {
        return true;
      }
    } else if (pObj instanceof Map) {
      if (((Map<?, ?>) pObj).size() == 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * 判断对象是否为NotEmpty(!null或元素>0)<br>
   * 
   * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
   * 
   * @param pObj 待检查对象
   * @return boolean 返回的布尔值
   */

  public static boolean isNotEmpty(Object pObj) {
    if (pObj == "null") {
      return false;
    }
    if (pObj == null) {
      return false;
    }
    if (pObj == "") {
      return false;
    }
    if (pObj instanceof String) {
      if (((String) pObj).length() == 0) {
        return false;
      }
    } else if (pObj instanceof Collection) {
      if (((Collection<?>) pObj).size() == 0) {
        return false;
      }
    } else if (pObj instanceof Map) {
      if (((Map<?, ?>) pObj).size() == 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * 
   * 将一个 Map 对象转化为一个 JavaBean
   * 
   * @param type 要转化的类型
   * @param map 包含属性值的 map
   * @return 转化出来的 JavaBean 对象
   * @throws IntrospectionException 如果分析类属性失败
   * @throws IllegalAccessException 如果实例化 JavaBean 失败
   * @throws InstantiationException 如果实例化 JavaBean 失败
   * @throws InvocationTargetException 如果调用属性的 setter 方法失败
   * 
   */

  public static Object convertMap(Class<?> type, Map<?, ?> map) throws IntrospectionException,
      IllegalAccessException, InstantiationException, InvocationTargetException {
    BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
    Object obj = type.newInstance(); // 创建 JavaBean 对象
    // 给 JavaBean 对象的属性赋值
    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < propertyDescriptors.length; i++) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      if (map.containsKey(propertyName)) {
        // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
        Object value = map.get(propertyName);
        Object[] args = new Object[1];
        args[0] = value;
        descriptor.getWriteMethod().invoke(obj, args);
      }
    }
    return obj;
  }



  /**
   * 
   * 将一个 JavaBean 对象转化为一个 Map
   * 
   * 
   * 
   * @param bean 要转化的JavaBean 对象
   * @return 转化出来的 Map 对象
   * @throws IntrospectionException 如果分析类属性失败
   * @throws IllegalAccessException 如果实例化 JavaBean 失败
   * @throws InvocationTargetException 如果调用属性的 setter 方法失败
   * 
   */
  public static Map<String, Object> convertBean(Object bean)
      throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    Class<? extends Object> type = bean.getClass();
    Map<String, Object> returnMap = new HashMap<String, Object>();
    BeanInfo beanInfo = Introspector.getBeanInfo(type);
    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < propertyDescriptors.length; i++) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      if (!propertyName.equals("class")) {
        Method readMethod = descriptor.getReadMethod();
        Object result = readMethod.invoke(bean, new Object[0]);
        if (result != null) {
          returnMap.put(propertyName, result);
        } else {
          returnMap.put(propertyName, "");
        }
      }
    }
    return returnMap;
  }



  /**
   * 
   * 判断参数列表中是否有null或有空（主要用于入参为空检测） 1、只要有一个为null或者为空，则返回ture 2、当全部不为null或空时，返回false
   * 
   * 
   * 
   * @param objects 待检测参数
   * 
   * @return true,false
   * 
   */

  public static boolean hasNull(Object... objects) {
    if (null == objects || objects.length == 0) {
      return true;
    }
    try {
      for (Object object : objects) {
        if (null == object) {
          return true;
        }
        if (object instanceof String && ((String) object).trim().length() == 0) {
          return true;
        } else if (object instanceof Map && ((Map<?, ?>) object).size() == 0) {
          return true;
        } else if (object instanceof Enumeration && !((Enumeration<?>) object).hasMoreElements()) {
          return true;
        } else if (object instanceof Iterator && !((Iterator<?>) object).hasNext()) {
          return true;
        } else if (object instanceof Collection && ((Collection<?>) object).isEmpty()) {
          return true;
        } else if (object.getClass().isArray() && Array.getLength(object) == 0) {
          return true;
        }
      }
      return false;// 所有的参数均不为null或空时，返回false
    } catch (Exception e) {
      return true;
    }
  }



  /**
   * 判断参数列表中是否全部为空（主要用于入参为空检测）
   * 
   * @param objects 待检测参数
   * @return true,false
   */

  public static boolean isAllNull(Object... objects) {
    if (null == objects || objects.length == 0) {
      return true;
    }
    try {
      for (Object object : objects) {
        if (null == object) {
          continue;
        }
        if (object instanceof String && ((String) object).trim().length() > 0) {
          return false;
        } else if (object instanceof Map && ((Map<?, ?>) object).size() > 0) {
          return false;
        } else if (object instanceof Enumeration && ((Enumeration<?>) object).hasMoreElements()) {
          return false;
        } else if (object instanceof Iterator && ((Iterator<?>) object).hasNext()) {
          return false;
        } else if (object instanceof Collection && !((Collection<?>) object).isEmpty()) {
          return false;
        } else if (object.getClass().isArray() && Array.getLength(object) > 0) {
          return false;
        }
      }
      return true;// 所有的参数均为空时，返回true
    } catch (Exception e) {
      return false;
    }

  }


  /**
   * 驼峰转下划线
   * 
   * @param param需要转换的字符串
   * @param charType 2为大写，其他为小写
   */
  public static String camelToUnderline(String param, Integer charType) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append(UNDERLINE);
      }
      if (charType == 2) {
        sb.append(Character.toUpperCase(c)); // 统一都转大写
      } else {
        sb.append(Character.toLowerCase(c)); // 统一都转小写
      }


    }
    return sb.toString();
  }

  /**
   * 下划线转驼峰
   * 
   * @param param需要转换的字符串
   */
  public static String underlineToCamel(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    Boolean flag = false; // "_" 后转大写标志,默认字符前面没有"_"
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (c == UNDERLINE) {
        flag = true;
        continue; // 标志设置为true,跳过
      } else {
        if (flag == true) {
          // 表示当前字符前面是"_" ,当前字符转大写
          sb.append(Character.toUpperCase(param.charAt(i)));
          flag = false; // 重置标识
        } else {
          sb.append(Character.toLowerCase(param.charAt(i)));
        }
      }
    }
    return sb.toString();
  }

  public static List<String> getTreePath(String str) throws Exception {
    int split = 4;
    int length = str.length();
    int mod = length % split;
    if (mod != 0) {
      throw new Exception("数据异常");
    }
    List<String> re = new ArrayList<>();
    int start = 0;
    int i = 1;
    while (start < length) {
      String s = str.substring(0, i * split);
      re.add(s);
      start = i * split;
      i++;
    }
    return re;
  }

  // 将时间转换为时间戳
  public static String dateToStamp(String s) throws ParseException {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = simpleDateFormat.parse(s);
    long ts = date.getTime();
    res = String.valueOf(ts);
    return res;
  }

  // 将时间戳转换为时间
  public static String stampToDate(String s) {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long lt = new Long(s);
    Date date = new Date(lt);
    res = simpleDateFormat.format(date);
    return res;
  }
  /**
   * 随机数字码生成
   * 
   * @param num
   * @return
   */
  public static String randCode(int num) {
    Random random = new Random();

    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < num; i++) {
      sb.append(random.nextInt(10));
    }
    return sb.toString();
  }
}
