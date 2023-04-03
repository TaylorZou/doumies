package com.doumies.ocs.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.doumies.common.result.Result;
import com.doumies.ocs.pojo.dto.OrderDTO;
import com.doumies.ocs.pojo.entity.OcsOrder;
import com.doumies.ocs.pojo.entity.OcsOrderItem;
import com.doumies.ocs.pojo.query.OrderPageQuery;
import com.doumies.ocs.service.IOrderItemService;
import com.doumies.ocs.service.IOrderService;
import com.doumies.ucs.api.MemberFeignClient;
import com.doumies.ucs.pojo.dto.MemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author Taylor
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "「系统端」订单管理")
@RestController("adminOrderController")
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    private final IOrderItemService orderItemService;
    private final MemberFeignClient memberFeignClient;

    @ApiOperation("订单列表")
    @GetMapping
    public Result listOrdersWithPage(OrderPageQuery queryParams) {
        IPage<OcsOrder> result = orderService.listOrdersWithPage(queryParams);
        return Result.success(result.getRecords(), result.getTotal());
    }


    @ApiOperation(value = "订单详情")
    @GetMapping("/{orderId}")
    public Result getOrderDetail(
            @ApiParam("订单ID") @PathVariable Long orderId
    ) {
        OrderDTO orderDTO = new OrderDTO();
        // 订单
        OcsOrder order = orderService.getById(orderId);

        // 订单明细
        List<OcsOrderItem> orderItems = orderItemService.list(
                new LambdaQueryWrapper<OcsOrderItem>().eq(OcsOrderItem::getOrderId, orderId)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(new ArrayList<>());

        // 会员明细
        Result<MemberDTO> result = memberFeignClient.getUserById(order.getMemberId());
        MemberDTO member = result.getData();
        orderDTO.setOrder(order).setOrderItems(orderItems).setMember(member);
        return Result.success(orderDTO);
    }

}
