package com.doumies.mcs.controller.app;

import com.doumies.common.result.Result;
import com.doumies.common.web.util.JwtUtils;
import com.doumies.mcs.pojo.domain.McsCoupon;
import com.doumies.mcs.pojo.vo.CouponTemplateVO;
import com.doumies.mcs.pojo.vo.McsCouponVO;
import com.doumies.mcs.service.IMcsCouponService;
import com.doumies.mcs.service.IMcsCouponTemplateService;
import com.doumies.mcs.util.BeanMapperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @desc: 用户优惠券
 * @author Taylor
 * @date 2021/3/14
 */
@Api(tags = "「移动端」优惠券")
@RestController
@RequestMapping("/api-app/v1/coupon/")
@Slf4j
public class AppCouponController {

    @Autowired
    private IMcsCouponService couponService;

    @Autowired
    private IMcsCouponTemplateService couponTemplateService;

    @ApiOperation("查看可领取优惠券模板列表")
    @GetMapping("/template")
    public Result<List<CouponTemplateVO>> findAvailableTemplate() {
        List<CouponTemplateVO> availableTemplate = couponService.findAvailableTemplate(JwtUtils.getUserId());
        return Result.success(availableTemplate);
    }

    @ApiOperation("用户领取优惠券")
    @GetMapping("/receive")
    public Result receive(@ApiParam(value = "优惠券模板ID")
                          @RequestParam("templateId") String templateId) {
        couponService.receive(JwtUtils.getUserId(), templateId);
        return Result.success();
    }

    @ApiOperation("查询用户已领取优惠券列表")
    @GetMapping("/list")
    public Result<List<McsCouponVO>> list(@ApiParam(value = "优惠券模板ID", defaultValue = "1")
                                          @RequestParam(value = "state", required = false) Integer state) {
        List<McsCoupon> coupons = couponService.findCouponsByState(JwtUtils.getUserId(), state);
        return Result.success(BeanMapperUtils.mapList(coupons, McsCouponVO.class));
    }

}
