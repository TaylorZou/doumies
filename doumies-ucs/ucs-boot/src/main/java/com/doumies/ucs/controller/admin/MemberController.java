package com.doumies.ucs.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.common.constant.GlobalConstants;
import com.doumies.common.result.Result;
import com.doumies.ucs.pojo.entity.UcsMember;
import com.doumies.ucs.service.IUcsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Api(tags = "「系统端」会员管理")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final IUcsMemberService iUcsMemberService;

    @ApiOperation(value = "会员分页列表")
    @GetMapping
    public Result<List<UcsMember>> list(
            @ApiParam("页码") Long pageNum,
            @ApiParam("每页数量") Long pageSize,
            @ApiParam("会员昵称") String nickName
    ) {
        IPage<UcsMember> result = iUcsMemberService.list(new Page<>(pageNum, pageSize), nickName);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "获取会员详情")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<UcsMember> getMemberDetail(
            @ApiParam("会员ID") @PathVariable Long id
    ) {
        UcsMember user = iUcsMemberService.getById(id);
        return Result.success(user);
    }

    @ApiOperation(value = "修改会员")
    @PutMapping(value = "/{id}")
    public <T> Result<T> update(
            @ApiParam("会员ID") @PathVariable Long id,
            @RequestBody UcsMember member
    ) {
        boolean status = iUcsMemberService.updateById(member);
        return Result.judge(status);
    }

    @ApiOperation(value = "选择性修改会员")
    @PatchMapping("/{id}")
    public <T> Result<T> patch(
            @ApiParam("会员ID") @PathVariable Long id,
            @RequestBody UcsMember member
    ) {
        boolean status = iUcsMemberService.update(new LambdaUpdateWrapper<UcsMember>()
                .eq(UcsMember::getId, id)
                .set(member.getStatus() != null, UcsMember::getStatus, member.getStatus())
        );
        return Result.judge(status);
    }

    @ApiOperation(value = "删除会员")
    @DeleteMapping("/{ids}")
    public <T> Result<T> delete(
            @ApiParam("会员ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        boolean status = iUcsMemberService.update(new LambdaUpdateWrapper<UcsMember>()
                .in(UcsMember::getId, Arrays.asList(ids.split(",")))
                .set(UcsMember::getDeleted, GlobalConstants.STATUS_YES));
        return Result.judge(status);
    }
}
