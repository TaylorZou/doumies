package com.doumies.ocs.pojo.vo;

import com.doumies.common.base.BaseVO;
import lombok.Data;

/**
 * @desc 订单创建响应结果VO
 * @author Taylor
 * @date 2021-12-13
 */
@Data
public class OrderSubmitVO extends BaseVO {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号，进入支付页面显示
     */
    private String orderSn;

}
