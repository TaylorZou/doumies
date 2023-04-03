package com.doumies.pcs.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.dto.admin.GoodsFormDTO;
import com.doumies.pcs.pojo.entity.PcsSpu;
import com.doumies.pcs.pojo.vo.admin.GoodsDetailVO;
import com.doumies.pcs.service.IPcsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 「系统端」商品信息
 *
 * @author Taylor
 * @date 2023/2/28
 **/
@Api(tags = "「系统端」商品信息")
@RestController
@RequestMapping("/api/v1/goods")
@AllArgsConstructor
public class GoodsController {

    private IPcsSpuService iPcsSpuService;

    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public Result list(
            @ApiParam(value = "页码", example = "1") long pageNum,
            @ApiParam(value = "每页数量", example = "10") long pageSize,
            @ApiParam("商品分类ID") Long categoryId,
            @ApiParam("商品名称") String name
    ) {
        IPage<PcsSpu> result = iPcsSpuService.list(new Page<>(pageNum, pageSize), name, categoryId);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "商品详情")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(
            @ApiParam("商品ID") @PathVariable Long id
    ) {
        GoodsDetailVO goodsDetail = iPcsSpuService.getGoodsById(id);
        return Result.success(goodsDetail);
    }

    @ApiOperation(value = "新增商品")
    @PostMapping
    public Result addGoods(
            @RequestBody GoodsFormDTO goodsForm
    ) {
        boolean result = iPcsSpuService.addGoods(goodsForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改商品")
    @PutMapping(value = "/{id}")
    public Result update(
            @ApiParam("商品ID") @PathVariable Long id,
            @RequestBody GoodsFormDTO goods
    ) {
        boolean result = iPcsSpuService.updateGoods(goods);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping("/{ids}")
    public Result delete(
            @ApiParam("id集合,以英文逗号(,)分隔") @PathVariable String ids
    ) {
        boolean result = iPcsSpuService.removeByGoodsIds(Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改商品")
    @PatchMapping(value = "/{id}")
    public Result patch(
            @ApiParam("商品ID") @PathVariable Long id,
            @RequestBody PcsSpu spu
    ) {
        LambdaUpdateWrapper<PcsSpu> updateWrapper = new LambdaUpdateWrapper<PcsSpu>().eq(PcsSpu::getId, id);
        updateWrapper.set(spu.getStatus() != null, PcsSpu::getStatus, spu.getStatus());
        boolean update = iPcsSpuService.update(updateWrapper);
        return Result.success(update);
    }
}
