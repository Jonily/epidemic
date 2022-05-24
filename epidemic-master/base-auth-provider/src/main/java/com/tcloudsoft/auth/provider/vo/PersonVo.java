package com.tcloudsoft.auth.provider.vo;

import com.tcloudsoft.auth.provider.model.Person;
import lombok.Data;

@Data
public class PersonVo extends Person {

    /**
     * 健康码
     */
    private String codeName;

    /**
     * 身份证
     */
    private String showCard;

    /**
     * 行政区域
     */
    private String showOrigin;

    /**
     * 所处社区
     */
    private String deptName;

}
