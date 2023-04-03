package com.doumies.ocs.pojo.dto;

import lombok.Data;

/**
 * @desc 订单提交实体类
 * @author Taylor
 * @date 2021-12-13
 */
@Data
public class OrderConfirmDTO {

    private Long skuId;

    private Integer count;

}
