package com.doumies.admin.controller.v2;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.admin.pojo.entity.SysDict;
import com.doumies.admin.pojo.entity.SysDictItem;
import com.doumies.admin.service.ISysDictItemService;
import com.doumies.admin.service.ISysDictService;
import com.doumies.common.result.Result;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/api/v2/dict")
@RequiredArgsConstructor
public class DictV2Controller {

    private final ISysDictService iSysDictService;
    private final ISysDictItemService iSysDictItemService;

    @ApiOperation(value = "字典分页列表")
    @GetMapping("/page")
    public Result listDictByPage(
            @ApiParam("页码") long pageNum,
            @ApiParam("每页数量") long pageSize,
            @ApiParam("字典名称") String name
    ) {
        Page<SysDict> result = iSysDictService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysDict>()
                        .like(StrUtil.isNotBlank(name), SysDict::getName, StrUtil.trimToNull(name))
                        .orderByDesc(SysDict::getGmtModified)
                        .orderByDesc(SysDict::getGmtCreate));
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "字典详情")
    @GetMapping("/{id}")
    public Result getDictDetail(
            @ApiParam("字典ID") @PathVariable Long id
    ) {
        SysDict dict = iSysDictService.getById(id);
        return Result.success(dict);
    }

    @ApiOperation(value = "新增字典")
    @PostMapping
    public Result addDict(@RequestBody SysDict dict) {
        boolean status = iSysDictService.save(dict);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改字典")
    @PutMapping("/{id}")
    public Result updateDict(@PathVariable Long id, @RequestBody SysDict dict) {
        boolean status = iSysDictService.updateDictById(id, dict);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除字典")
    @DeleteMapping("/{ids}")
    public Result deleteDict(
            @ApiParam("字典ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = iSysDictService.deleteDictByIds(ids);
        return Result.judge(result);
    }

    @ApiOperation(value = "字典项分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "dictCode", value = "字典编码", paramType = "query", dataType = "String")
    })
    @GetMapping("/items/page")
    public Result getPageList(
            long pageNum,
            long pageSize,
            String name,
            String dictCode
    ) {
        IPage<SysDictItem> result = iSysDictItemService.list(
                new Page<>(pageNum, pageSize),
                new SysDictItem().setName(name).setDictCode(dictCode)
        );
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "根据字典编码获取字典项列表")
    @GetMapping("/items")
    public Result list(String dictCode) {
        List<SysDictItem> list = iSysDictItemService.list(
                new LambdaQueryWrapper<SysDictItem>()
                        .eq(StrUtil.isNotBlank(dictCode), SysDictItem::getDictCode, dictCode)
                        .select(SysDictItem::getName, SysDictItem::getValue)
                        .orderByAsc(SysDictItem::getSort)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "字典项详情")
    @GetMapping("/items/{id}")
    public Result getDictItemDetail(@PathVariable Long id) {
        SysDictItem dictItem = iSysDictItemService.getById(id);
        return Result.success(dictItem);
    }

    @ApiOperation(value = "新增字典项")
    @PostMapping("/items")
    public Result add(@RequestBody SysDictItem dictItem) {
        boolean status = iSysDictItemService.save(dictItem);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改字典项")
    @PutMapping(value = "/items/{id}")
    public Result update(@PathVariable Long id, @RequestBody SysDictItem dictItem) {
        boolean status = iSysDictItemService.updateById(dictItem);
        return Result.judge(status);
    }


    @ApiOperation(value = "删除字典项")
    @ApiImplicitParam(name = "ids", value = "以,分割拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/items/{ids}")
    public Result deleteDictItem(@PathVariable String ids) {
        boolean result = iSysDictItemService.removeByIds(StrUtil.split(ids, ','));
        return Result.judge(result);
    }
}
