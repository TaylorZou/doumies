package com.doumies.mcs.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.common.result.Result;
import com.doumies.mcs.pojo.McsAdvert;
import com.doumies.mcs.service.IMcsAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "「系统端」营销广告")
@RestController("AdminAdvertController")
@RequestMapping("/api/v1/adverts")
@Slf4j
@RequiredArgsConstructor
public class AdvertController {

    private final IMcsAdvertService iMcsAdvertService;

    @ApiOperation(value = "列表分页")
    @GetMapping
    public Result getAdvertPageList(
            @ApiParam("页码") Long pageNum,
            @ApiParam("每页数量") Long pageSize,
            @ApiParam("广告标题") String title) {
        Page<McsAdvert> result = iMcsAdvertService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<McsAdvert>()
                        .like(StrUtil.isNotBlank(title), McsAdvert::getTitle, StrUtil.isNotBlank(title)?title:null)
                        .orderByAsc(McsAdvert::getSort)
                        .orderByDesc(McsAdvert::getGmtModified)
        );
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "广告详情")
    @GetMapping("/{id}")
    public Result getAdvertDetail(
            @ApiParam("广告ID") @PathVariable Long id) {
        McsAdvert advert = iMcsAdvertService.getById(id);
        return Result.success(advert);
    }

    @ApiOperation(value = "新增广告")
    @PostMapping
    public Result addAvert(@RequestBody McsAdvert advert) {
        boolean status = iMcsAdvertService.save(advert);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改广告")
    @PutMapping(value = "/{id}")
    public Result updateAdvert(
            @ApiParam("广告ID") @PathVariable Long id,
            @RequestBody McsAdvert advert) {
        boolean status = iMcsAdvertService.updateById(advert);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除广告")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        boolean status = iMcsAdvertService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

    @ApiOperation(value = "选择性更新广告")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody McsAdvert advert) {
        LambdaUpdateWrapper<McsAdvert> updateWrapper = new LambdaUpdateWrapper<McsAdvert>().eq(McsAdvert::getId, id);
        updateWrapper.set(advert.getStatus() != null, McsAdvert::getStatus, advert.getStatus());
        boolean result = iMcsAdvertService.update(updateWrapper);
        return Result.judge(result);
    }
}
