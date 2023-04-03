package com.doumies.ocs.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @desc Order RabbitMQ 业务类
 * @author Taylor
 * @date 2021-12-13
 */
public interface OrderRabbitService {

    /**
     * 订单超时释放
     *
     * @param orderSn
     * @param message
     * @param channel
     */
    void releaseOrder(String orderSn, Message message, Channel channel);
}
