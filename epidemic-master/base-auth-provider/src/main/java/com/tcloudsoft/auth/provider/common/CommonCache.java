package com.tcloudsoft.auth.provider.common;

import java.util.Hashtable;

public class CommonCache {
  private static Hashtable<String, String> dataMap = new Hashtable<>();

  public static void put(String name, String value) {
    dataMap.put(name, value);
  }

  public static String get(String name) {
    return dataMap.get(name);
  }

  public static void clear() {
    dataMap.clear();
  }
}
