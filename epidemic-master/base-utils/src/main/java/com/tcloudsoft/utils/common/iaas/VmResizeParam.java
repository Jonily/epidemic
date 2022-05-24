package com.tcloudsoft.utils.common.iaas;

import java.util.List;
import com.tcloudsoft.utils.common.UserInfo;
import lombok.Data;

@Data
public class VmResizeParam extends Param {
  private String vmId;
  private Integer cpu;
  private Integer cpuNum;
  private Integer memory;
  private Integer disk;
  private Boolean powerOn = false;
  private List<DiskParam> disks;
  private List<NetworkCardParam> nics;
  private String vmName;
  // 腾讯云用
  private String instanceType;
  private String region;
  // 流程用
  private String wfId;
  private String name;

  private UserInfo userVo;

}
