package com.doumies.ocs;

import com.doumies.pcs.api.GoodsFeignClient;
import com.doumies.ucs.api.MemberFeignClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = { MemberFeignClient.class, GoodsFeignClient.class})
@EnableRabbit
@EnableTransactionManagement
public class OcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcsApplication.class, args);
    }

}
