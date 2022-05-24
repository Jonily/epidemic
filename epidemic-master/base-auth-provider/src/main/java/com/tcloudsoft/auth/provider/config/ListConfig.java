package com.tcloudsoft.auth.provider.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;


@Data
@Component
@ConfigurationProperties(prefix = "write")
public class ListConfig {
  private List<String> list;


}
