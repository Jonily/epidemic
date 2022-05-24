package com.tcloudsoft.utils.Excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Excel操作 实体类注解
 * @since 2021-10-21
 * @author liuwei
 *
 * <>
 *    用来标识需要读取的excel对应的实体类属性相关信息
 *
 * </>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GrainExcelModel {

    /**
     * 对应excel上的名称
     * @return String
     */
    String name();

    /**
     * 对应excel上的下标
     * @return String
     */
    String index();

    /**
     * 是否非必填
     *
     * 默认为 false
     * @return boolean
     */
    boolean isNotNull() default false;


    /**
     * 是否在 excel 中唯一
     * 默认为 false
     * @return
     */
    boolean isOnLy() default false;




}
