package com.tcloudsoft.tcms.gateway.provider.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "write")
@Data
public class ListConfig {
  private List<String> list;
}
