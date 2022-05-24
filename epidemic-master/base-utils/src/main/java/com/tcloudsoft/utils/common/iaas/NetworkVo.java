package com.tcloudsoft.utils.common.iaas;

import com.alibaba.fastjson.JSONObject;
import com.tcloudsoft.utils.TcloudUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NetworkVo {
  private String id;
  private String name;
  private String vlanId;
  private String description;
  private String status;
  private String serviceId;
  private String serviceName;
  private Boolean enable;
  private String vswitch;
  private String mask;
  private String gateway;
  private String dns;
  private String backDns;
  private String subnet;
  private String poolId;
  private String uuid;
  private String type;
  private String freeIpId;
  private String extra;
  private String key;
  private String ip;
  private PortProfileDto portProfileDto;

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
    NetworkVo other = (NetworkVo) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }


}
