package com.doumies.ocs.pojo.dto;

import com.doumies.common.base.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单商品
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO extends BaseVO {

    private Long skuId;
    private String skuName;
    private String skuCode;
    private Integer count;
    private String pic;
    private Long price;

    private String spuName;
}
