package com.doumies.mcs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @desc 异步+定时 配置
 * @author Taylor
 * @date 2021/6/27
 */
@EnableScheduling
@EnableAsync
@Configuration
public class ScheduledConfig {
}
