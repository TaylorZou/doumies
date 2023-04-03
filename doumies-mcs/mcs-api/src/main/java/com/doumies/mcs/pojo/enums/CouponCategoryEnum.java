package com.doumies.mcs.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @desc: 优惠券分类
 * @author Taylor
 * @date 2021/3/10
 */
@Getter
@AllArgsConstructor
public enum CouponCategoryEnum {

    FULLREDUCE("满减券", "001"),
    DISCOUNT("折扣券", "002"),
    IMMEDIATEREDUCE("立减券", "003"),
    ;

    /**
     * 优惠券描述（分类）
     */
    @JsonValue
    private String desc;

    /**
     * 优惠券分类编码
     */
    @EnumValue
    private String code;

    public static CouponCategoryEnum of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " code not exist"));
    }
}
