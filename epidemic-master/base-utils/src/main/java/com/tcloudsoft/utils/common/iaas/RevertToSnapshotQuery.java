package com.tcloudsoft.utils.common.iaas;

import lombok.Data;

@Data
public class RevertToSnapshotQuery extends Param {
  String hostId;
  String snapshotId;
  String vmId;
}
