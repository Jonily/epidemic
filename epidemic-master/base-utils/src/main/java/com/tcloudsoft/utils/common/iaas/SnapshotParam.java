package com.tcloudsoft.utils.common.iaas;

import lombok.Data;

@Data
public class SnapshotParam extends Param {
  String vmId;
  String name;
  String desc;
  Boolean memory;
  String snapshotId;
  String description;
  String hostId;

}
