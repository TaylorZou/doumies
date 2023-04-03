package com.doumies.common.redis.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @desc redisson 连接配置类
 * @author Taylor
 * @date 2023/2/27
 */
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    private String serverAddress;

    private String port;

    private String password;

    private Integer database;

    public Integer getDatabase() {
        if (null == database) {
            return 0;
        }
        return database;
    }
}
