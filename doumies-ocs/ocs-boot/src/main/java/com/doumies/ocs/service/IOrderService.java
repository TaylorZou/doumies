package com.doumies.ocs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.doumies.ocs.enums.PayTypeEnum;
import com.doumies.ocs.pojo.dto.OrderConfirmDTO;
import com.doumies.ocs.pojo.dto.OrderSubmitDTO;
import com.doumies.ocs.pojo.entity.OcsOrder;
import com.doumies.ocs.pojo.query.OrderPageQuery;
import com.doumies.ocs.pojo.vo.OrderConfirmVO;
import com.doumies.ocs.pojo.vo.OrderSubmitVO;

/**
 * 订单业务接口
 *
 * @author Taylor
 * @date 2021-12-13
 */
public interface IOrderService extends IService<OcsOrder> {

    /**
     * 订单确认
     */
    OrderConfirmVO confirm(OrderConfirmDTO orderConfirmDTO);

    /**
     * 订单提交
     */
    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO) ;

    /**
     * 订单提交
     */
    OrderSubmitVO submitTcc(OrderSubmitDTO orderSubmitDTO) ;

    /**
     * 订单支付
     */
    <T> T pay(Long orderId, String appId, PayTypeEnum payTypeEnum);

    /**
     * 系统关闭订单
     */
    boolean closeOrder(String orderToken);

    /**
     * 取消订单接口
     */
    boolean cancelOrder(Long id);

    /**
     * 删除订单
     */
    boolean deleteOrder(Long id);

    /**
     * 处理微信支付成功回调
     *
     * @param signatureHeader 签名头
     * @param notifyData      加密通知
     * @throws WxPayException 微信异常
     */
    void handleWxPayOrderNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException;

    /**
     * 处理微信退款成功回调
     *
     * @param signatureHeader 签名头
     * @param notifyData      加密通知
     * @throws WxPayException 微信异常
     */
    void handleWxPayRefundNotify(SignatureHeader signatureHeader, String notifyData) throws WxPayException;

    /**
     * 订单分页列表
     *
     * @param queryParams
     * @return
     */
    IPage<OcsOrder> listOrdersWithPage(OrderPageQuery queryParams);
}

