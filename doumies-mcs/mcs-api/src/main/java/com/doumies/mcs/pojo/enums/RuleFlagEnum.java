package com.doumies.mcs.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc: 结算规则类型枚举
 * @author Taylor
 * @date 2021/3/10
 */
@Getter
@AllArgsConstructor
public enum  RuleFlagEnum {
    // 单类型优惠券定义
    FULLREDUCE("满减券计算规则"),

    DISCOUNT("折扣卷计算规则"),

    IMMEDIATEREDUCE("立减券计算规则"),

    // 多类别组合优惠券定义
    FULLREDUCE_DISCOUNT("满减券+折扣券计算规则"),
    ;

    /**
     * 规则描述
     */
    private String desc;
}
