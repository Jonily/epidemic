package com.tcloudsoft.utils.common;

import lombok.Getter;

@Getter
public enum AdviceModule {
  CONFIG("配置风险 "), CAPACITY("容量检查"), PERFORMACE("性能风险"), PLATFORM("平台建议"), STATUS("状态异常");

  private String label;

  private AdviceModule(String label) {
    this.label = label;
  }

}
