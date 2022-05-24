package com.tcloudsoft.web.provider.vo;

import com.tcloudsoft.web.provider.model.Visit;
import lombok.Data;


@Data
public class VisitVo extends Visit {

    /**
     * 行程人姓名
     */
    private String visitPersonName;

    /**
     * 行程人手机号码
     */
    private String visitPhone;

    /**
     * 来源地址
     */
    private String fromAddress;

    /**
     * 目标地址
     */
    private String targetAddress;

    /**
     * 健康码状态
     */
    private Integer codeStatus;

    /**
     * 健康吗
     */
    private String codeName;

    /**
     *  交通方式
     */
    private String transportation;

    /**
     *  今日来访行程总数
     */
    private Integer visitCount = 0;// 默认0

    /**
     * 今日出行总数
     */
    private Integer tripCount = 0;// 默认0

    /**
     * 历史来访总数
     */
    private Integer visitCountHistory = 0;// 默认0

    /**
     * 历史出行总数
     */
    private Integer tripCountHistory = 0;// 默认0
}
