package com.doumies.ucs.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.doumies.common.result.Result;
import com.doumies.common.result.ResultCode;
import com.doumies.common.web.util.JwtUtils;
import com.doumies.pcs.pojo.vo.ProductHistoryVO;
import com.doumies.ucs.pojo.dto.MemberAuthDTO;
import com.doumies.ucs.pojo.dto.MemberDTO;
import com.doumies.ucs.pojo.entity.UcsMember;
import com.doumies.ucs.service.IUcsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@Api(tags = "「移动端」会员管理")
@RequestMapping("/app-api/v1/members")
@RestController("appMemberController")
@RequiredArgsConstructor
public class MemberController {

    private final IUcsMemberService iUcsMemberService;

    @ApiOperation(value = "获取会员信息")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<MemberDTO> getById(@PathVariable Long id) {
        MemberDTO memberDTO = new MemberDTO();
        UcsMember user = iUcsMemberService.getOne(
                new LambdaQueryWrapper<UcsMember>()
                        .select(UcsMember::getId, UcsMember::getNickName, UcsMember::getMobile, UcsMember::getBalance)
                        .eq(UcsMember::getId, id)
        );
        if (user != null) {
            BeanUtil.copyProperties(user, memberDTO);
        }
        return Result.success(memberDTO);
    }

    @ApiOperation(value = "获取会员实体信息")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/detail/{id}")
    public Result<UcsMember> getMemberEntityById(
            @PathVariable Long id
    ) {
        UcsMember user = iUcsMemberService.getById(id);
        if (user == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(user);
    }


    @ApiOperation(value = "新增会员")
    @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    @PostMapping
    public Result<Long> add(@RequestBody UcsMember member) {
        boolean status = iUcsMemberService.save(member);
        if (status) {
            return Result.success(member.getId());
        } else {
            return Result.failed();
        }
    }

    @ApiOperation(value = "修改会员")
    @PutMapping("/{id}")
    public <T> Result<T> add(@PathVariable Long id, @RequestBody UcsMember user) {
        boolean status = iUcsMemberService.updateById(user);
        return Result.judge(status);
    }

    @ApiOperation(value = "获取登录会员信息")
    @GetMapping("/me")
    public Result<MemberDTO> getMemberInfo() {
        Long userId = JwtUtils.getUserId();
        UcsMember member = iUcsMemberService.getById(userId);
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        MemberDTO memberDTO = new MemberDTO();
        BeanUtil.copyProperties(member, memberDTO);
        return Result.success(memberDTO);
    }


    @ApiOperation(value = "修改会员积分")
    @PutMapping("/{id}/points")
    public <T> Result<T> updatePoint(@PathVariable Long id, @RequestParam Integer num) {
        UcsMember user = iUcsMemberService.getById(id);
        user.setPoint(user.getPoint() + num);
        boolean result = iUcsMemberService.updateById(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减会员余额")
    @PutMapping("/current/balances/_deduct")
    public <T> Result<T> deductBalance(@RequestParam Long balances) {
        Long userId = JwtUtils.getUserId();
        boolean result = iUcsMemberService.update(new LambdaUpdateWrapper<UcsMember>()
                .setSql("balance = balance - " + balances)
                .eq(UcsMember::getId, userId)
        );
        return Result.judge(result);
    }

    @ApiOperation(value = "添加浏览历史")
    @PostMapping("/view/history")
    public <T> Result<T> addProductViewHistory(@RequestBody ProductHistoryVO product) {
        Long userId = JwtUtils.getUserId();
        iUcsMemberService.addProductViewHistory(product, userId);
        return Result.success();
    }

    @ApiOperation(value = "获取浏览历史")
    @GetMapping("/view/history")
    public Result<Set<ProductHistoryVO>> getProductViewHistory() {
        try {
            Long userId = JwtUtils.getUserId();
            Set<ProductHistoryVO> historyList = iUcsMemberService.getProductViewHistory(userId);
            return Result.success(historyList);
        } catch (Exception e) {
            return Result.success(Collections.emptySet());
        }
    }

    @ApiOperation(value = "根据openid获取会员认证信息")
    @ApiImplicitParam(name = "openid", value = "微信身份唯一标识", required = true, paramType = "path", dataType = "String")
    @GetMapping("/openid/{openid}")
    public Result<MemberAuthDTO> getByOpenid(@PathVariable String openid) {
        UcsMember member = iUcsMemberService.getOne(new LambdaQueryWrapper<UcsMember>()
                .eq(UcsMember::getOpenid, openid)
                .select(UcsMember::getId, UcsMember::getOpenid, UcsMember::getStatus)
        );
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        MemberAuthDTO memberAuth = new MemberAuthDTO(member.getId(), member.getOpenid(), member.getStatus());
        return Result.success(memberAuth);
    }

    @ApiOperation(value = "根据手机号获取会员认证信息")
    @ApiImplicitParam(name = "mobile", value = "会员手机号码", required = true, paramType = "path", dataType = "String")
    @GetMapping("/mobile/{mobile}")
    public Result<MemberAuthDTO> getByMobile(@PathVariable String mobile) {
        UcsMember member = iUcsMemberService.getOne(new LambdaQueryWrapper<UcsMember>()
                .eq(UcsMember::getMobile, mobile)
                .select(UcsMember::getId, UcsMember::getMobile, UcsMember::getStatus)
        );
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        MemberAuthDTO memberAuth = new MemberAuthDTO(member.getId(), member.getMobile(), member.getStatus());
        return Result.success(memberAuth);
    }

}
