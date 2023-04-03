package com.doumies.ocs.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 订单分页对象
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Data
public class OrderPageVO {

    private Long id;

    private String orderSn;

    private Long totalAmount;

    private Long payAmount;

    private Integer payType;

    private Integer status;

    private Integer totalQuantity;

    private Date gmtCreate;

    private Long memberId;

    private Integer sourceType;

    private List<OrderItem> orderItems;

    @Data
    public static class OrderItem {

        private Long id;

        private Long orderId;

        private Long skuId;

        private String skuName;

        private String skuPic;

        private Long skuPrice;

        private Integer skuQuantity;

        private Long skuTotalPrice;

        private String spuName;

    }

}
