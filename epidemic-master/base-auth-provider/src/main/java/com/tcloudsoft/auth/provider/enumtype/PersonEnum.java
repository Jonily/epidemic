package com.tcloudsoft.auth.provider.enumtype;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区用户数据库映射枚举
 * @since 2021-12-02
 */
@Getter
public enum PersonEnum {

    name("name","姓名"),
    idCard("showCard","身份证"),
    phone("phone","手机号码"),
    age("age","年龄"),
    province("province","住址(省)"),
    city("city","住址(市)"),
    district("district","住址(区县)"),
    street("street","住址(乡镇)"),
    village("village","住址(居委会)"),
    origin("origin","详细住址"),
    codeStatus("codeName","最新健康码");

    private String code;

    private String value;

    PersonEnum(String code, String value){
        this.code = code;
        this.value = value;
    }

    public static Map<String, List<String>> getFields(){
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        PersonEnum[] enums = PersonEnum.values();
        for (PersonEnum pe : enums){
            fields.add(pe.getCode());
            values.add(pe.getValue());
        }
        Map<String,List<String>> map = new HashMap<>();
        map.put("fields",fields);
        map.put("values",values);
        return map;
    }

}
