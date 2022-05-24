package com.tcloudsoft.utils.common.iaas;

import com.alibaba.fastjson.JSONObject;
import com.tcloudsoft.utils.TcloudUtils;
import lombok.Data;

@Data
public class ImageVo {
  private String id;
  private String vmId;
  private Integer cpu;

  private Boolean custom;

  private String description;

  private Long disk;

  private Boolean enable;

  private Integer memory;

  private String name;

  private String os;

  private String password;

  private String remark;

  private String serviceId;


  private String username;

  private String extra;

  public JSONObject getExtraJson() {
    if (TcloudUtils.isNotEmpty(this.getExtra())) {
      return JSONObject.parseObject(this.getExtra());
    }
    return null;
  }


}
