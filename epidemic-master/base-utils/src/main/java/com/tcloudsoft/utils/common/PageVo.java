package com.tcloudsoft.utils.common;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class PageVo<T> {

  private long total;
  private int current;
  private int pageSize;
  private long totalPage;
  private List<T> data;
  private Map<String, Object> extraData;

}
