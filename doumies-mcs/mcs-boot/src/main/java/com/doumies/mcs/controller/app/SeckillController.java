package com.doumies.mcs.controller.app;

import com.doumies.common.result.Result;
import com.doumies.mcs.pojo.vo.McsSeckillSkuVO;
import com.doumies.mcs.service.ISeckillService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @desc 秒杀活动管理
 * @author Taylor
 * @date 2021/3/14
 */
@Api(tags = "「移动端」秒杀活动管理")
@RestController("APPSeckillController")
@RequestMapping("/api-app/v1/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private ISeckillService ISeckillService;

    @GetMapping
    public Result getCurrentSeckillSession() {
        List<McsSeckillSkuVO> currentSeckills = ISeckillService.getCurrentSeckillSession();
        return Result.success();
    }
}
