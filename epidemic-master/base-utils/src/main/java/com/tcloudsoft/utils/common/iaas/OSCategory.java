package com.tcloudsoft.utils.common.iaas;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class OSCategory implements Serializable {
  private Integer id;
  private String name;
  private List<OSResource> osList;

}
