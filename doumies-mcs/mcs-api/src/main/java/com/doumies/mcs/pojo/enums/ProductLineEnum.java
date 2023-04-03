package com.doumies.mcs.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @desc: 产品线枚举
 * @author Taylor
 * @date 2021/3/10
 */
@Getter
@AllArgsConstructor
public enum ProductLineEnum {

    YOULAI("有来", 1),
    WUHUI("无回", 2),
    ;

    /**
     * 产品线描述
     */
    @JsonValue
    private String desc;

    /**
     * 产品线编码
     */
    @EnumValue
    private Integer code;

    public static ProductLineEnum of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " code not exist"));
    }

}
