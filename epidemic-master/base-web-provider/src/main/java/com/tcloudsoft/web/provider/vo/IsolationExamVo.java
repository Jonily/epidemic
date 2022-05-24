package com.tcloudsoft.web.provider.vo;

import com.tcloudsoft.web.provider.model.IsolationExam;
import lombok.Data;

@Data
public class IsolationExamVo extends IsolationExam {

    private String name;

    private String idCard;

    private String phone;

    private String type;


}
