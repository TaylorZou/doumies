package com.doumies.common.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Taylor
 * @date 2023/2/27 23:04
 */
@ConfigurationProperties(prefix = "aliyun.sms")
@Configuration
@Data
public class AliyunSmsProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String domain;

    private String regionId;

    private String templateCode;

    private String signName;

}
