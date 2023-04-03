package com.doumies.pcs.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.entity.PcsBrand;
import com.doumies.pcs.service.IPcsBrandService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Taylor
 * @date 2023/2/28
 */
@Api(tags = "「系统端」品牌信息")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IPcsBrandService iPcsBrandService;

    @ApiOperation(value = "品牌列表分页")
    @GetMapping("/page")
    public Result getBrandPageList(
            @ApiParam("页码") Long pageNum,
            @ApiParam("每页数量") Long pageSize,
            @ApiParam("品牌名称") String name
    ) {
        LambdaQueryWrapper<PcsBrand> queryWrapper = new LambdaQueryWrapper<PcsBrand>()
                .like(StrUtil.isNotBlank(name), PcsBrand::getName, name)
                .orderByDesc(PcsBrand::getGmtModified)
                .orderByDesc(PcsBrand::getGmtCreate);
        Page<PcsBrand> result = iPcsBrandService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "品牌列表")
    @GetMapping
    public Result getBrandList() {
        List<PcsBrand> list = iPcsBrandService.list(new LambdaQueryWrapper<PcsBrand>()
                .select(PcsBrand::getId, PcsBrand::getName));
        return Result.success(list);
    }

    @ApiOperation(value = "品牌详情")
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getBrandList(@PathVariable Integer id) {
        PcsBrand brand = iPcsBrandService.getById(id);
        return Result.success(brand);
    }

    @ApiOperation(value = "新增品牌")
    @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    @PostMapping
    public Result addBrand(@RequestBody PcsBrand brand) {
        boolean status = iPcsBrandService.save(brand);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PutMapping(value = "/{id}")
    public Result updateBrand(
            @PathVariable Long id,
            @RequestBody PcsBrand brand) {
        boolean status = iPcsBrandService.updateById(brand);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除品牌")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, dataType = "String")
    @DeleteMapping("/{ids}")
    public Result deleteBrands(@PathVariable("ids") String ids) {
        boolean status = iPcsBrandService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}
