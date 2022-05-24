package com.tcloudsoft.utils.utils;

import java.lang.reflect.Field;
import java.util.Date;

public class ReflectionUtil {

    /*
    * 赋值
    * @param entity 实体
     * @param name 名称字段名称
     * @param date  时间字段名称
     * @param typeName 名称对应的值
    * @author: tangjia
    * @createDate: 2020/12/16 17:25
    * @return: T
    */
    public static  <T> T setfields(T entity, String name, String date,String typeName) {
        Class<?> clazz = entity.getClass();
        try {
            // 获取字段
            Field getName = clazz.getDeclaredField(name);
            Field getDate = clazz.getDeclaredField(date);

            // 开通权限
            getName.setAccessible(true);
            getDate.setAccessible(true);

            // 赋值
            getName.set(entity, typeName);
            getDate.set(entity, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回，强转一下（拿出去之后强转也一样，但是返回值得改成obj类型）
        return entity;
    }
}
