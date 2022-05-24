package com.tcloudsoft.utils;

import java.util.UUID;

public class UuidGenerator {
  public static synchronized String generator() {
    return UUID.randomUUID().toString();
  }
}
