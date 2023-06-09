package com.doumies.pcs.config;

import com.doumies.pcs.interceptor.BloomFilterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Taylor
 * @Description
 * @Date 2023/2/26 9:39
 **/
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        registry.addInterceptor(bloomInterceptorHandler())
                .addPathPatterns("/app-api/v1/goods/**")
                .excludePathPatterns("/app-api/v1/goods/sku/**");
    }

    @Bean
    public BloomFilterInterceptor bloomInterceptorHandler() {
        return new BloomFilterInterceptor();
    }
}
