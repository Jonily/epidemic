package com.tcloudsoft.utils.common.iaas;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import com.tcloudsoft.utils.TcloudUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorsInfo {
  private static Properties wsAddr = new Properties();
  static {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream stream = loader.getResourceAsStream("errorsInfo.properties");
    InputStreamReader re = new InputStreamReader(stream);
    if (re != null) {
      try {
        wsAddr.load(re);
      } catch (IOException e) {
        log.info("加载properties文件失败", e.getMessage());
      }
    }
  }

  public static Properties getWsAddr() {
    return wsAddr;
  }

  public static String getWsAddr(String key) {
    if (TcloudUtils.isEmpty(wsAddr.getProperty(key))) {
      return "执行失败";
    } else {
      return wsAddr.getProperty(key);
    }
  }
}
