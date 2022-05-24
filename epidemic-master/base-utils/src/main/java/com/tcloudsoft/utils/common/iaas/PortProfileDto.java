package com.tcloudsoft.utils.common.iaas;

import lombok.Data;

/**
 * 网络策略模板
 * 
 * @author tanbin
 *
 */
@Data
public class PortProfileDto {
  /**
   * 网络策略模板 ID。 长整数（long）类型。
   */
  Long id;
  /**
   * 网络策略模板名称。 字符串（String）类型。
   */
  String name;
  /**
   * 描述信息
   */
  String description;
  /**
   * vlanId
   */
  Long vlanId;
  /**
   * 入方向流量限制：1表示限制，0表示不限制。 整数（int）类型。
   */
  Integer inbound;// 创建时间
  /**
   * 入方向流量控制：平均带宽，单位Kbps 长整数（long）类型。
   */
  Long inAvgBandwidth;// 创建时间
  /**
   * 入方向流量控制：突发缓冲，单位Kbps 长整数（long）类型。
   */
  Long inBurstSize;
  /**
   * 出方向流量限制：1表示限制，0表示不限制。 整数（int）类型。
   */
  Integer outbound;
  /**
   * 出方向流量控制：平均带宽，单位Kbps 长整数（long）类型。
   */
  Long outAvgBandwidth;
  /**
   * 出方向流量控制：突发缓冲，单位Kbps 长整数（long）类型。
   */
  Long outBurstSize;
  /**
   * 绑定的ACL ID
   */
  Long aclStrategyId;

  /**
   * 绑定的ACL名称 字符串（String）类型。
   */
  String aclName;


}
