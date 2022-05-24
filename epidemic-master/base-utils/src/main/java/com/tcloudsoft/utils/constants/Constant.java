package com.tcloudsoft.utils.constants;

/**
 * @author Administrator
 * @title: aa
 * @projectName SpringBlade
 * @description: TODO
 * @date 2019/9/12 00128:58
 */

public class Constant {
  /**
   * 超级管理员ID
   */
  public static final int SUPER_ADMIN = 1;
  /**
   * 数据权限过滤
   */
  public static final String SQL_FILTER = "sql_filter";
  /**
   * 当前页码
   */
  public static final String CURRENT = "current";
  /**
   * 每页显示记录数
   */
  public static final String SIZE = "size";
  /**
   * 排序字段
   */
  public static final String ORDER_FIELD = "sidx";
  /**
   * 排序方式
   */
  public static final String ORDER = "order";
  /**
   * 升序
   */
  public static final String ASC = "asc";

  /**
   * 超级管理员租户ID
   */
  public static final String ADMIN_TENANT_ID = "000000";

  /**
   * 不删除
   */
  public static final int DB_NOT_DELETED = 0;
  /**
   * 逻辑删除
   */
  public static final int DB_IS_DELETED = 1;
  /**
   * 未锁定
   */
  public static final int DB_ADMIN_NON_LOCKED = 0;
  /**
   * 已锁定
   */
  public static final int DB_ADMIN_LOCKED = 1;

  public static final String IP = "192.168.14.156";

  public static final int PORT = 21;

  public static final String USERNAME = "pacs";

  public static final String PASSWORD = "standout";

  // 入库标识
  public static final String ENTER = "1";

  // 出库标识
  public static final String OUT = "2";

  //入库数据名称
  public static final String ENTER_DATA = "t_warehouse_enter";
  //入库数据名称
  public static final String OUT_DATA = "t_warehouse_out";

  public static final int DISTRICT = 7; // 区县

  public static final int CITY = 4; // 市

  public static final int STREET = 9; // 乡镇长度

  public static final int STREET1 = 10; // 乡镇长度

  public static final int VILLAGE = 12; // 村长度
  //当部门长度大于这个常量时，进行单位的转换
  public static final int  VILLAGE_U =  9;

  public static final String  W_D = "万吨"; // 万吨

  public static final String DUN = "吨"; // 吨

  //地图描点长度 6 (以市为基准，长度小于6 为市 ，大于的为乡镇区)
  public static final int  COORDINATE_6 =  6;

  public static final String PROCE = "大米加工企业"; // 加工厂

  public static final String CONVER = "转化加工企业"; // 饲料厂

  public static final String SELL = "销售"; // 销售

  public static final String STORAGE = "存储"; // 存储

  public static final String MU = "亩"; // 亩

  public static final String WMU = "万亩"; // 万亩

  public static final String CADMIUM_CONTENT_A = "A类"; // A类粮

  public static final String CADMIUM_CONTENT_B = "B类"; // B类粮

  public static final String CADMIUM_CONTENT_C = "C类"; // C类粮

  public static final String STATUS_TRUE = "1"; // 有效

  public static final String STATUS_FALSE = "0"; // 无效

  public static final String GRAIN_NATURE_1 = "1" ;//ZC(中储粮)

  public static final String GRAIN_NATURE_2 = "2"; //ZD(最低价粮)

  public static final String GRAIN_NATURE_3 = "3"; //SC(省储粮)

  public static final String GRAIN_NATURE_4 = "4"; //LC(临储粮)

  public static final String GRAIN_NATURE_5 = "5"; //XC(县储粮)

  public static final String GRAIN_NATURE_6 = "6"; //SC(市储粮)

  public static final String GRAIN_NATURE_7 = "7"; //SP(商品粮)

  public static final String  GRAIN = "GRZIN"; // 粮库管理员对应的角色


  public static final Double  A = 0.2; // 表示低于0.2的A类粮食  0.2-0.4为B类粮食

  public static final Double  C = 0.4; // 表示超过0.4的是C类粮食


  /**
   * 特殊字符匹配正则表达式
   */
  public static final String COMMON_REGEX = "[`~!@#$%^&*()_+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？']";;

  /**
   * 微信请求TOKEN地址
   */

  public static final  String WX_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";

  /**
   * 微信请求ticket地址
   */
  public static final String WX_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";



}
