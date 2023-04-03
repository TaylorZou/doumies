package com.doumies.ucs.api;

import com.doumies.common.result.Result;
import com.doumies.ucs.pojo.entity.UcsAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "doumies-ucs",contextId = "address")
public interface MemberAddressFeignClient {

    /**
     * 获取地址详情
     */
    @GetMapping("/app-api/v1/addresses/{id}")
    Result<UcsAddress> getById(@PathVariable("id") Long id);


    /**
     * 获取会员地址列表
     *
     * @param memberId
     * @return
     */
    @GetMapping("/app-api/v1/addresses")
    Result<List<UcsAddress>> list(@RequestParam Long memberId);

}


