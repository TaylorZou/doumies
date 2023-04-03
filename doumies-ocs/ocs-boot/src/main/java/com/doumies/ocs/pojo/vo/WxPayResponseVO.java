package com.doumies.ocs.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Taylor
 * @date 2021-12-13
 */
@Data
@Accessors(chain = true)
public class WxPayResponseVO {
    private String code;

    private String message;
}
