package com.doumies.common.rabbitmq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Taylor
 * @desc
 * @date 2023/2/27
 */
@Configuration
@EnableTransactionManagement
public class RabbitMQConfig {
    /**
     * 使用json序列化机制，进行消息转换
     *
     * @return
     */
    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
