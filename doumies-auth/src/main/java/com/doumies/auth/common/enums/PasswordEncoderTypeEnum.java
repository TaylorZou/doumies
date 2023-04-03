package com.doumies.auth.common.enums;

import lombok.Getter;

/**
 * @author Taylor
 * @description TODO
 * @createTime 2023/2/26 17:57
 */

public enum PasswordEncoderTypeEnum {

    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文");

    @Getter
    private String prefix;

    PasswordEncoderTypeEnum(String prefix, String desc){
        this.prefix=prefix;
    }

}
