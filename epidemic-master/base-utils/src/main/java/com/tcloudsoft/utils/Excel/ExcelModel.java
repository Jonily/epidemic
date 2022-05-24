package com.tcloudsoft.utils.Excel;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExcelModel extends ExcelExportEntity implements Serializable {

    private static final long serialVersionUID = 987323438321344L;

    private String fieldEnName; // 列英文名

    private String fieldName; // 列中文名

    private String fieldType; // 列的类型

    private  boolean isNotNull = false;// 是否必须 默认非必须

    private boolean isOnly = false;// 是否在 excel 中唯一

    private Integer index;// 所在excel的下标


    // 通用构造注入方法
    public ExcelModel(String fieldEnName, String fieldName, String fieldType){
        this.fieldEnName = fieldEnName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    // 导出实体构造方法 先中文名 再英文名
    public ExcelModel(String fieldName, String fieldEnName){
        super.name = fieldName; //赋值给父类的属性
        super.setKey(fieldEnName);// 赋值给父类的属性
    }

    // 传class的构造方法
    public ExcelModel(String fieldEnName, String fieldName, Class fieldType){
        this.fieldEnName = fieldEnName;
        this.fieldName = fieldName;
        String typeName = fieldType.getTypeName().substring(fieldType.getTypeName().lastIndexOf(".")+1);
        this.fieldType = typeName;
    }


    // 注解属性使用的构造方法
    public ExcelModel(String fieldEnName, String fieldName,boolean isNotNull,boolean isOnly,Integer index,String type){
        this.fieldEnName = fieldEnName;
        this.fieldName = fieldName;
        //截取实际数据类型字符串
        String typeName = type.substring(type.lastIndexOf(".")+1);
        this.fieldType = typeName;
        this.isNotNull = isNotNull;
        this.isOnly = isOnly;
        this.index = index;
    }

    // 加入非空判断的构造注入方法
    public ExcelModel(String fieldEnName, String fieldName, String fieldType, boolean isNotNull){
        this.fieldEnName = fieldEnName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.isNotNull = isNotNull;
    }


    public ExcelModel() { }
}
