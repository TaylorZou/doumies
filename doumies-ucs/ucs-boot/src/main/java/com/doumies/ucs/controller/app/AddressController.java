package com.doumies.ucs.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.doumies.common.result.Result;
import com.doumies.common.web.util.JwtUtils;
import com.doumies.ucs.pojo.entity.UcsAddress;
import com.doumies.ucs.service.IUcsAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "「移动端」会员地址")
@RestController
@RequestMapping("/app-api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IUcsAddressService iUcsAddressService;

    @ApiOperation(value = "获取会员地址列表")
    @GetMapping
    public Result<List<UcsAddress>> listAddresses() {
        List<UcsAddress> addressList = iUcsAddressService.list(new LambdaQueryWrapper<UcsAddress>()
                .eq(UcsAddress::getMemberId, JwtUtils.getUserId())
                .orderByDesc(UcsAddress::getDefaulted));
        return Result.success(addressList);
    }
    @ApiOperation(value = "获取地址详情")
    @GetMapping("/{addressId}")
    public Result<UcsAddress> getAddressDetail(
            @ApiParam("会员地址ID") @PathVariable Long addressId
    ) {
        UcsAddress ucsAddress = iUcsAddressService.getById(addressId);
        return Result.success(ucsAddress);
    }

    @ApiOperation(value = "新增地址")
    @PostMapping
    public Result addAddress(
            @RequestBody @Validated UcsAddress address
    ) {
        boolean result = iUcsAddressService.addAddress(address);
        return Result.judge(result);
    }


    @ApiOperation(value = "修改地址")
    @PutMapping("/{addressId}")
    public Result updateAddress(
            @ApiParam(value = "地址ID") @PathVariable Long addressId,
            @RequestBody @Validated UcsAddress address
    ) {
        boolean result = iUcsAddressService.updateAddress(address);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除地址")
    @DeleteMapping("/{ids}")
    public Result deleteAddress(
            @ApiParam("地址ID，过个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = iUcsAddressService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

}
