package com.tcloudsoft.utils.common.iaas;

import java.io.Serializable;
import lombok.Data;

@Data
public class OSResource implements Serializable {
  private String id;
  private String name;
  private String category;
  private String version;
  private String image;
  private String agent;
  private String clazz;
  private String uninstall;
}
