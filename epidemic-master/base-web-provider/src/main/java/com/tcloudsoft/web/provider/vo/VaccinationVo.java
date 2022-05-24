package com.tcloudsoft.web.provider.vo;

import com.tcloudsoft.web.provider.model.Vaccination;
import lombok.Data;

@Data
public class VaccinationVo extends Vaccination {

    /**
     * 接种人员姓名
     */
    private String personName;

    /**
     * 疫苗品牌名称
     */
    private String vaccinesName;

    /**
     * 审核状态 已审核/未审核
     */
    private String statusName;

}
