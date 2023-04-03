package com.doumies.ocs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc 订单来源类型枚举
 * @author Taylor
 * @createTime 2023/3/1 11:59
 */

@AllArgsConstructor
public enum OrderTypeEnum  {

    WEB(0), // PC订单
    APP(1), // APP订单
    ;

    @Getter
    private Integer code;

    public static OrderTypeEnum getValue(Integer code){
        for (OrderTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
