package com.doumies.ocs.pojo.dto;

import com.doumies.ucs.pojo.entity.UcsAddress;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @desc 订单提交
 * @author Taylor
 * @date 2021-12-13
 */
@Data
public class OrderSubmitDTO {

    // 提交订单确认页面签发的令牌
    private String orderToken;

    private List<OrderItemDTO> orderItems;

    // 验价前台传值
    private Long totalPrice;

    // 收货地址
    private UcsAddress deliveryAddress;

    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;

    private String couponId;

    private Long payAmount;

}
