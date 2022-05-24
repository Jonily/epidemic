package com.tcloudsoft.utils.common.iaas;

import com.alibaba.fastjson.JSONObject;
import com.tcloudsoft.utils.TcloudUtils;
import lombok.Data;

@Data
public class ZoneVo {
  private String id;
  private String name;
  private String description;
  private String serviceId;
  private boolean state;
  private String extra;

  public JSONObject getExtraJson() {
    if (TcloudUtils.isNotEmpty(this.getExtra())) {
      return JSONObject.parseObject(this.getExtra());
    }
    return null;
  }
}
