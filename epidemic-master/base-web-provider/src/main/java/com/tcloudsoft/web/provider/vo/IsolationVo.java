package com.tcloudsoft.web.provider.vo;

import com.tcloudsoft.web.provider.model.Isolation;
import lombok.Data;

@Data
public class IsolationVo extends Isolation {

    private String personName;

    private String isolationName;

    private boolean exam;

}
