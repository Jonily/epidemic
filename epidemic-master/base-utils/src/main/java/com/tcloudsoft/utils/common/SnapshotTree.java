package com.tcloudsoft.utils.common;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SnapshotTree {
  private String id;
  private String name;
  private String desc;
  private Long created;
  private boolean current;
  private List<SnapshotTree> children = new ArrayList<SnapshotTree>();

  public void addSnapshotTree(SnapshotTree snapshotTree) {
    if (this.children == null) {
      this.children = new ArrayList<SnapshotTree>();
    }
    this.children.add(snapshotTree);
  }


}
