package com.tcloudsoft.web.provider.enumType;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum  VaccinationEnum {

    idCard("idCard","接种人身份证"),
    personName("personName","接种人姓名"),
    time("time","接种时间"),
    area("area","接种地点"),
    brand("vaccinesName","疫苗品牌"),
    inoculationNum("inoculationNum","已接种针数"),
    statusName("statusName","审核状态");

    private String code;

    private String value;

    VaccinationEnum(String code,String value){
        this.code = code;
        this.value = value;
    }

    public static Map<String, List<String>> getFields(){
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        VaccinationEnum[] enums = VaccinationEnum.values();
        for (VaccinationEnum pe : enums){
            fields.add(pe.getCode());
            values.add(pe.getValue());
        }
        Map<String,List<String>> map = new HashMap<>();
        map.put("fields",fields);
        map.put("values",values);
        return map;
    }

}
