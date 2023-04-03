package com.doumies.mcs.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.doumies.common.result.Result;
import com.doumies.mcs.pojo.McsAdvert;
import com.doumies.mcs.service.IMcsAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "「移动端」营销广告")
@RestController("APPAdvertController")
@RequestMapping("/app-api/v1/adverts")
@Slf4j
@AllArgsConstructor
public class AdvertController {

    private IMcsAdvertService iMcsAdvertService;

    @ApiOperation(value = "列表分页")
    @GetMapping
    public Result list() {
        LambdaQueryWrapper<McsAdvert> queryWrapper = new LambdaQueryWrapper<McsAdvert>()
                .orderByAsc(McsAdvert::getSort);
        List<McsAdvert> data = iMcsAdvertService.list(queryWrapper);
        return Result.success(data);
    }
}
