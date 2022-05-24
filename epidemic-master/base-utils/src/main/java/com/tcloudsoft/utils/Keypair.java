package com.tcloudsoft.utils;

import lombok.Data;

@Data
public class Keypair {
  private String key;
  private Object value;
  private String type;// 自动发现类型默认API

  public Keypair() {}

  public Keypair(String key, Object value) {
    this.key = key;
    this.value = value;
  }

}

