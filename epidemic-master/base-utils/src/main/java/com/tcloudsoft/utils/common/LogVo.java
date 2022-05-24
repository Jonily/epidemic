package com.tcloudsoft.utils.common;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LogVo {
  private String id;
  private String appId;
  private String operate;
  private String ip;
  private String userName;
  private String name;
  private String userId;
  private Date created;
  private String targetName;
  private String targetId;
  private Boolean success;
  private String description;
  private String original;
  private String latest;
  private Long duration;
}
