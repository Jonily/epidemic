package com.tcloudsoft.utils.common.iaas;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NetworkCardParam {
  private String id;
  private String name;
  private String networkId;
  private String network;// ==networkId 前端传值要求
  private String networkType;
  private String swId;
  private String swUuid;
  private String networkName;
  private String key;
  private Boolean delete = false;
  private Boolean edit = false;

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
    NetworkCardParam other = (NetworkCardParam) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }


}
