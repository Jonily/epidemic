package com.tcloudsoft.utils.common.iaas;

import lombok.Data;

@Data
public class NetworkCardVo {
  private String id;
  private String name;
  private String ip;
  private String serviceId;
  private String mac;
  private String network;
  private String refId;
  private String networkName;
}
