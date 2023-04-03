package com.doumies.pcs.api;

import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.dto.app.SkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "doumies-pcs",contextId = "goods")
public interface GoodsFeignClient {

    /**
     * 获取商品信息
     */
    @GetMapping("/app-api/v1/stocks/{id}")
    Result<SkuDTO> getSkuById(@PathVariable Long id);
}
