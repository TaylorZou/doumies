package com.doumies.admin.controller;

import com.doumies.admin.pojo.entity.SysDept;
import com.doumies.admin.pojo.vo.IdLabelVO;
import com.doumies.admin.pojo.vo.dept.DeptVO;
import com.doumies.admin.service.ISysDeptService;
import com.doumies.common.result.Result;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 *
 * @author Taylor
 * @date 2023-2-25
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("/api/v1/depts")
@RequiredArgsConstructor
public class DeptController {

    private final ISysDeptService deptService;

    @ApiOperation(value = "部门表格（Table）层级列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "部门状态", paramType = "query", dataType = "Long"),
    })
    @GetMapping("/table")
    public Result getTableList(Integer status, String name) {
        List<DeptVO> deptTableList = deptService.listTable(status, name);
        return Result.success(deptTableList);
    }

    @ApiOperation(value = "部门下拉（TreeSelect）层级列表")
    @GetMapping("/select")
    public Result getSelectList() {
        List<IdLabelVO> deptSelectList = deptService.listTreeSelect();
        return Result.success(deptSelectList);
    }

    @ApiOperation(value = "部门详情")
    @GetMapping("/{deptId}")
    public Result getDeptDetail(
            @ApiParam("部门ID") @PathVariable Long deptId
    ) {
        SysDept sysDept = deptService.getById(deptId);
        return Result.success(sysDept);
    }

    @ApiOperation(value = "新增部门")
    @PostMapping
    public Result addDept(@RequestBody SysDept dept) {
        Long id = deptService.saveDept(dept);
        return Result.success(id);
    }

    @ApiOperation(value = "修改部门")
    @PutMapping(value = "/{deptId}")
    public Result updateDept(
            @ApiParam("部门ID") @PathVariable Long deptId,
            @RequestBody SysDept dept
    ) {
        deptId = deptService.saveDept(dept);
        return Result.success(deptId);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{ids}")
    public Result deleteDepartments(
            @ApiParam("部门ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids
    ) {
        boolean status = deptService.deleteByIds(ids);
        return Result.judge(status);
    }

}
