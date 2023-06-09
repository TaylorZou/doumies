package com.doumies.pcs.api;

import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.dto.app.LockStockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "doumies-pcs",contextId = "stock")
public interface StockFeignClient {

    /**
     * 锁定库存
     */
    @PutMapping("/app-api/v1/stocks/_lock")
    Result lockStock(@RequestBody List<LockStockDTO> list);

    /**
     * 解锁库存
     */
    @PutMapping("/app-api/v1/stocks/_unlock")
    Result<Boolean> unlockStock(@RequestParam String orderToken);


    /**
     * 扣减库存
     */
    @PutMapping("/app-api/v1/stocks/_deduct")
    Result deductStock(@RequestParam String orderToken);


}
