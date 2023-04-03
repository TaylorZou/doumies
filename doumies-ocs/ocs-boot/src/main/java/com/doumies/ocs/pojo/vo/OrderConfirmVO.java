package com.doumies.ocs.pojo.vo;

import com.doumies.common.base.BaseVO;
import com.doumies.ocs.pojo.dto.OrderItemDTO;
import com.doumies.ucs.pojo.entity.UcsAddress;
import lombok.Data;

import java.util.List;


@Data
public class OrderConfirmVO extends BaseVO {

    private String orderToken;

    private List<OrderItemDTO> orderItems;

    private List<UcsAddress> addresses;

}
