package com.doumies.pcs.controller.admin;

import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.entity.PcsSku;
import com.doumies.pcs.service.IPcsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Taylor
 * @date 2023/2/28
 */
@Api(tags = "「系统端」库存信息")
@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class StockController {

    private final IPcsSkuService iPcsSkuService;

    @ApiOperation(value = "商品库存详情")
    @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        PcsSku sku = iPcsSkuService.getById(id);
        return Result.success(sku);
    }

    @ApiOperation(value = "修改库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sku", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSku")
    })
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable Long id, @RequestBody PcsSku sku) {
        boolean status = iPcsSkuService.updateById(sku);
        return Result.judge(status);
    }


    @ApiOperation(value = "修改商品库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "库存数量", required = true, paramType = "query", dataType = "Long")
    })
    @PatchMapping("/{id}/stock")
    public Result updateStock(@PathVariable Long id, @RequestParam Integer num) {
        PcsSku sku = iPcsSkuService.getById(id);
        sku.setStock(sku.getStock() + num);
        boolean result = iPcsSkuService.updateById(sku);
        return Result.judge(result);
    }
}
