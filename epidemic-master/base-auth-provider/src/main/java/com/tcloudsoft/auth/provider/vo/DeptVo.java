package com.tcloudsoft.auth.provider.vo;

import com.tcloudsoft.utils.TcloudUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class DeptVo {
  private String id;

  private String name;

  private String pid = "-1";

  private String code;

  private String description;

  private DeptVo parent;

  private List<DeptVo> children;

  private List<UserVo> users;

  private String tenantId;

  public List<DeptVo> getChildren() {
    if (TcloudUtils.isEmpty(this.children)) {
      return null;
    }
    return this.children;
  }
}
