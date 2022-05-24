package com.tcloudsoft.auth.provider.utils;

import com.tcloudsoft.auth.provider.model.Person;
import com.tcloudsoft.auth.provider.service.RegionService;
import com.tcloudsoft.auth.provider.vo.PersonVo;
import com.tcloudsoft.utils.TcloudUtils;

public class QueryUtils {

    public static String append(PersonVo person, RegionService regionService){
        StringBuffer sb = new StringBuffer();
        if (TcloudUtils.isNotEmpty(person.getProvince())){
            sb.append(regionService.getCodeByName(person.getProvince()));
        }
        if (TcloudUtils.isNotEmpty(person.getCity())){
            sb.append(regionService.getCodeByName(person.getCity()));
        }
        if (TcloudUtils.isNotEmpty(person.getDistrict())){
            sb.append(regionService.getCodeByName(person.getDistrict()));
        }
        if (TcloudUtils.isNotEmpty(person.getStreet())){
            sb.append(regionService.getCodeByName(person.getStreet()));
        }
        if (TcloudUtils.isNotEmpty(person.getVillage())){
            sb.append(regionService.getCodeByName(person.getVillage()));
        }
        if (TcloudUtils.isNotEmpty(person.getOrigin())){
            sb.append(person.getOrigin());
        }
        if (TcloudUtils.isEmpty(sb.toString())){
            return person.getOrigin();
        }
        return sb.toString();
    }

    /**
     * 身份证正确性验证 例如：41000119910101123X  410001 19910101 123X
     * //[1-9] 第一位1-9中的一个      4
     * //\\d{5} 五位数字           10001（前六位省市县地区）
     * //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
     * //\\d{2}                    91（年份）
     * //((0[1-9])|(10|11|12))     01（月份）
     * //(([0-2][1-9])|10|20|30|31)01（日期）
     * //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
     * //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
     * @param idCard
     * @author liuwei
     * @return boolean 正确为 true 错误为 false
     */
    public static boolean idCardVerification(String idCard){
        if (idCard == null || "".equals(idCard)) return false;
        idCard = idCard.trim();
        //身份证验证史上最全正则表达式 18位or15位身份证通用 详细见方法注释。。
        String regularExpression =
                "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                        "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

        boolean matches = idCard.matches(regularExpression);
        if(matches){//如果验证通过
            if(idCard.length() == 18){// 18位身份证
                try {
                    char[] charArray = idCard.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }
        //如果验证不通过 或者银行卡不是18位或15位 则 直接返回matches(false)
        return matches;
    }
}
