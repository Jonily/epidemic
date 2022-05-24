package com.tcloudsoft.utils.common;

import lombok.Getter;

@Getter
public enum AdviceObject {
  DATASTORE("数据存储"),

  ESXI("ESXi主机"),

  CLUSTER("计算集群"),

  VM("虚拟机"),

  VSWITCH("虚拟交换机"),

  VCENTER("vCenter"),

  CLOUDOS("CloudOS"),

  IMAGE("镜像"),

  DISK("磁盘"),

  CASHOST("CAS主机"),

  SERVICE("云服务"),

  CMP("云平台"),

  EASYSTACK("EasyStack");
  private String label;

  private AdviceObject(String label) {
    this.label = label;
  }
}
