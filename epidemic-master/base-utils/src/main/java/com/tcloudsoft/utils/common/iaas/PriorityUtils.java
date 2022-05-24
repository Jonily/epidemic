package com.tcloudsoft.utils.common.iaas;

public class PriorityUtils {
  public static String getPriorityStr(Long priority) {
    if (priority == null) {
      return "";
    }
    if (priority == 0) {
      return "";
    } else if (priority == 1) {
      return "common";
    } else if (priority == 2) {
      return "warning";
    } else if (priority == 3) {
      return "critical";
    } else if (priority == 4) {
      return "disaster";
    }
    return "";
  }
}
