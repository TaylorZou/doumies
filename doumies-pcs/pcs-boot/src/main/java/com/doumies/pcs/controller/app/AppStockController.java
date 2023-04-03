package com.doumies.pcs.controller.app;

import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.dto.app.LockStockDTO;
import com.doumies.pcs.pojo.dto.app.SkuDTO;
import com.doumies.pcs.service.IPcsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Stock Keeping Unit
 */
@Api(tags = "「移动端」商品库存")
@RestController(value = "appStockController")
@RequestMapping("/app-api/v1/stocks")
@AllArgsConstructor
public class AppStockController {

    private IPcsSkuService iPcsSkuService;

    @ApiOperation(value = "商品库存单元详情")
    @GetMapping("/{skuId}")
    public Result detail(@PathVariable Long skuId) {
        SkuDTO sku = iPcsSkuService.getSkuById(skuId);
        return Result.success(sku);
    }

    @ApiOperation("获取商品的库存数量")
    @GetMapping("/{skuId}/stock")
    public Result<Integer> getStockById(@PathVariable Long skuId) {
        Integer stock = iPcsSkuService.getStockById(skuId);
        return Result.success(stock);
    }

    @ApiOperation(value = "锁定库存")
    @PutMapping("/_lock")
    public Result lockStock(@RequestBody List<LockStockDTO> list) {
        return iPcsSkuService.lockStock(list);
    }


    @ApiOperation(value = "解锁库存")
    @PutMapping("/_unlock")
    public Result<Boolean> unlockStock(String orderToken) {
        boolean result = iPcsSkuService.unlockStock(orderToken);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减库存")
    @PutMapping("/_deduct")
    public Result<Boolean> deductStock(String orderToken) {
        boolean result = iPcsSkuService.deductStock(orderToken);
        return Result.judge(result);
    }
}
