package com.tcloudsoft.utils.common.iaas;

import lombok.Data;

@Data
public abstract class Param {
  private String serviceId;
  private String task;
  private String apiIp;// api调用IP地址
  private String apiUser;// api鉴权用户名
  private String apiPwd;// api鉴权密码
  private String apiPort;// api调用端口
  private String envId;// 云环境ID
  private String namespace;// 网络域
}
