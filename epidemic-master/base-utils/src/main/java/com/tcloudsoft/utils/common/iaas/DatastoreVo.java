package com.tcloudsoft.utils.common.iaas;

import com.alibaba.fastjson.JSONObject;
import com.tcloudsoft.utils.TcloudUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DatastoreVo {
  private String id;
  private int priority;
  private Long free = 0L;
  private Boolean share;
  private String name;
  private String serviceId;
  private String serviceName;
  private Long total = 0L;
  private Boolean accessible;
  private Long uncommit;
  private String extra;
  private String diskName;
  private Integer vmNum = 0;

  public JSONObject getExtraJson() {
    if (TcloudUtils.isNotEmpty(this.getExtra())) {
      return JSONObject.parseObject(this.getExtra());
    }
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DatastoreVo other = (DatastoreVo) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }


}
