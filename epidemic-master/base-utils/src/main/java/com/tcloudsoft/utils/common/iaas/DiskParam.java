package com.tcloudsoft.utils.common.iaas;

import lombok.Data;

@Data
public class DiskParam extends Param {
  private String id;
  private String name;
  private String datastore;
  private Long size;
  private String model;
  private Boolean delete = false;
  // KVM专用
  private String area;
}
