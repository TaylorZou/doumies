package com.doumies.ocs.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Taylor
 * @createTime 2023/3/1 11:59
 */
@FeignClient("doumies-ocs")
public interface OrderFeignClient {
}
