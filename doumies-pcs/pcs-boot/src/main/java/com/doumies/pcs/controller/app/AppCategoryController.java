package com.doumies.pcs.controller.app;

import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.vo.CategoryVO;
import com.doumies.pcs.service.IPcsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Taylor
 * @date 2023/2/28
 */
@Api(tags = "「移动端」商品分类")
@RestController("appCategoryController")
@RequestMapping("/app-api/v1/categories")
@Slf4j
@AllArgsConstructor
public class AppCategoryController {

    private IPcsCategoryService iPcsCategoryService;

    @ApiOperation(value = "分类列表")
    @ApiImplicitParam(name = "parentId",value = "上级分类ID", paramType = "query", dataType = "Long")
    @GetMapping
    public Result list(Long parentId) {
        List<CategoryVO> list = iPcsCategoryService.listCategory(parentId);
        return Result.success(list);
    }
}
