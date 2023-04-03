package com.doumies.ocs.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.common.result.Result;
import com.doumies.common.web.util.JwtUtils;
import com.doumies.ocs.enums.PayTypeEnum;
import com.doumies.ocs.pojo.dto.OrderConfirmDTO;
import com.doumies.ocs.pojo.dto.OrderSubmitDTO;
import com.doumies.ocs.pojo.entity.OcsOrder;
import com.doumies.ocs.pojo.query.OrderPageQuery;
import com.doumies.ocs.pojo.vo.OrderConfirmVO;
import com.doumies.ocs.pojo.vo.OrderSubmitVO;
import com.doumies.ocs.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Taylor
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "「移动端」订单管理")
@RestController
@RequestMapping("/app-api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    final IOrderService orderService;

    @ApiOperation("订单列表")
    @GetMapping
    public Result listOrdersWithPage(OrderPageQuery queryParams) {
        IPage<OcsOrder> result = orderService.page(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                new LambdaQueryWrapper<OcsOrder>()
                        .eq(OcsOrder::getStatus, queryParams.getStatus())
                        .eq(OcsOrder::getMemberId, JwtUtils.getUserId())
        );
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation("订单确认")
    @PostMapping("/_confirm")
    public Result<OrderConfirmVO> confirm(@RequestBody OrderConfirmDTO orderConfirm) {
        OrderConfirmVO result = orderService.confirm(orderConfirm);
        return Result.success(result);
    }

    @ApiOperation("订单提交")
    @PostMapping("/_submit")
    public Result submit(@Valid @RequestBody OrderSubmitDTO orderSubmitDTO) {
        OrderSubmitVO result = orderService.submit(orderSubmitDTO);
        return Result.success(result);
    }

    @ApiOperation("订单支付")
    @PostMapping("/{orderId}/_pay")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "payType", value = "支付方式", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "appId", value = "小程序appId", paramType = "query", dataType = "String")
    })
    public <T> Result<T> pay(@PathVariable Long orderId, Integer payType, String appId) {

        PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
        if (payTypeEnum == null) {
            return Result.failed("系统暂不支持该支付方式~");
        }
        return Result.success(orderService.pay(orderId, appId, payTypeEnum));
    }

    @ApiOperation("订单删除")
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean result = orderService.deleteOrder(orderId);
        return Result.judge(result);
    }

    @ApiOperation("订单取消")
    @PutMapping("/cancel")
    public Result cancel(@RequestParam Long id) {
        boolean result = orderService.cancelOrder(id);
        return Result.judge(result);
    }
}
