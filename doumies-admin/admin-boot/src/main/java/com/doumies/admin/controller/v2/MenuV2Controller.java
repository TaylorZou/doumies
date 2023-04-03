package com.doumies.admin.controller.v2;

import com.doumies.admin.pojo.vo.menu.NextRouteVO;
import com.doumies.admin.service.ISysMenuService;
import com.doumies.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单控制器V2版本适配 Vue3 的 vue-next-router
 *
 * @author Taylor
 * @date 2023/2/25
 */
@Api(tags = "菜单接口(V2)",value = "适配Vue3的vue-next-router")
@RestController
@RequestMapping("/api/v2/menus")
@RequiredArgsConstructor
public class MenuV2Controller {

    private final ISysMenuService menuService;

    @ApiOperation(value = "菜单路由(Route)层级列表")
    @GetMapping("/route")
    public Result getRouteList() {
        List<NextRouteVO> routeList = menuService.listNextRoutes();
        return Result.success(routeList);
    }
}
