package com.tcloudsoft.utils.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 健康检查环境认证参数封装
 * 
 * @author tanbin
 *
 */
@Data
public class HealthCheckEvnAuth implements Serializable {

  private static final long serialVersionUID = 8309704180225451639L;

  private String ip;
  private String userName;
  private String password;
}
