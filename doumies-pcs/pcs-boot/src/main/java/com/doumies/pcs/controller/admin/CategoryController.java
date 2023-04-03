package com.doumies.pcs.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.entity.PcsAttribute;
import com.doumies.pcs.pojo.entity.PcsCategory;
import com.doumies.pcs.pojo.vo.CategoryVO;
import com.doumies.pcs.service.IPcsAttributeService;
import com.doumies.pcs.service.IPcsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * * 「系统端」商品分类控制器
 *
 * @author Taylor
 * @date 2023/2/28
 */
@Api(tags = "「系统端」商品分类")
@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private IPcsCategoryService iPcsCategoryService;
    private IPcsAttributeService iPcsAttributeService;

    @ApiOperation(value = "商品分类列表")
    @GetMapping
    public Result<List<CategoryVO>> list() {
        List<CategoryVO> list = iPcsCategoryService.listCategory(null);
        return Result.success(list);
    }

    @ApiOperation(value = "商品分类级联列表")
    @GetMapping("/cascade")
    public Result listCascadeCategory() {
        List list = iPcsCategoryService.listCascadeCategory();
        return Result.success(list);
    }

    @ApiOperation(value = "商品分类详情")
    @GetMapping("/{id}")
    public Result detail(
            @ApiParam("商品分类ID") @PathVariable Long id
    ) {
        PcsCategory category = iPcsCategoryService.getById(id);
        return Result.success(category);
    }

    @ApiOperation(value = "新增商品分类")
    @PostMapping
    public Result addCategory(@RequestBody PcsCategory category) {
        Long id = iPcsCategoryService.saveCategory(category);
        return Result.success(id);
    }

    @ApiOperation(value = "修改商品分类")
    @PutMapping(value = "/{id}")
    public Result update(
            @ApiParam("商品分类ID") @PathVariable Long id,
            @RequestBody PcsCategory category
    ) {
        category.setId(id);
        id = iPcsCategoryService.saveCategory(category);
        return Result.success(id);
    }

    @ApiOperation(value = "删除商品分类")
    @ApiImplicitParam(name = "ids", value = "id集合,以英文逗号','分隔", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    @CacheEvict(value = "com/doumies/pcs", key = "'categoryList'")
    public Result delete(@PathVariable String ids) {
        List<String> categoryIds = Arrays.asList(ids.split(","));
        iPcsAttributeService.remove(new LambdaQueryWrapper<PcsAttribute>().in(CollectionUtil.isNotEmpty(categoryIds), PcsAttribute::getCategoryId, categoryIds));
        boolean result = iPcsCategoryService.removeByIds(categoryIds);
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改商品分类")
    @PatchMapping(value = "/{id}")
    @CacheEvict(value = "com/doumies/pcs", key = "'categoryList'")
    public Result patch(@PathVariable Long id, @RequestBody PcsCategory category) {
        LambdaUpdateWrapper<PcsCategory> updateWrapper = new LambdaUpdateWrapper<PcsCategory>().eq(PcsCategory::getId, id);
        updateWrapper.set(category.getVisible() != null, PcsCategory::getVisible, category.getVisible());
        boolean result = iPcsCategoryService.update(updateWrapper);
        return Result.judge(result);
    }
}
