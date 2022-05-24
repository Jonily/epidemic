package com.tcloudsoft.web.provider.util;

import cn.hutool.core.date.DateUtil;
import com.tcloudsoft.utils.TcloudUtils;

import java.math.BigDecimal;
import java.util.Date;

public class CovertUtils {

    /**
     * 计算两数相除结果 保留 2 位小数
     * @return 百分数
     */
    public static Double percent(Integer p1,Integer p2){
        if (null == p1 || 0 == p1){
            return 0d;
        }
        if (null == p2 || 0 == p2){
            return 0d;
        }
        BigDecimal param1 = new BigDecimal(p1);
        BigDecimal param2 = new BigDecimal(p2);
        BigDecimal result = param1.divide(param2,4,BigDecimal.ROUND_HALF_UP);
        BigDecimal value = result.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        return value.doubleValue();
    }


    public static void main(String[] args) {
        String time = "2021-12-09 11:22:11";
        Date date = DateUtil.parse(time,"yyyy-MM-dd HH:mm:ss");
        System.out.println(DateUtil.format(date,"dd"));
        Integer num = Integer.parseInt(DateUtil.format(date,"dd"));
        System.out.println(num);
    }
}
