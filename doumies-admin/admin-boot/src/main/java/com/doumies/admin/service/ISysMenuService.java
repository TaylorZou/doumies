package com.doumies.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.admin.pojo.entity.SysMenu;
import com.doumies.admin.pojo.vo.*;
import com.doumies.admin.pojo.vo.menu.MenuVO;
import com.doumies.admin.pojo.vo.menu.NextRouteVO;
import com.doumies.admin.pojo.vo.menu.RouteVO;

import java.util.List;

/**
 * @author Taylor
 * @date 2023-2-25
 */
public interface ISysMenuService extends IService<SysMenu> {


    /**
     * 菜单表格(Table)层级列表
     *
     * @param name 菜单名称
     * @return
     */
    List<MenuVO> listTable(String name);


    /**
     * 菜单下拉(Select)层级列表
     *
     * @return
     */
    List<ValueLabelVO> listSelect();


    /**
     * 菜单路由(Route)层级列表
     *
     * @return
     */
    List<RouteVO> listRoute();

    /**
     * 菜单下拉(TreeSelect)层级列表
     *
     * @return
     */
    List<IdLabelVO> listTreeSelect();

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    boolean saveMenu(SysMenu menu);


    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    boolean updateMenu(SysMenu menu);

    /**
     * 清理路由缓存
     */
    void cleanCache();

    /**
     * 获取路由列表(适配Vue3的vue-next-router)
     *
     * @return
     */
    List<NextRouteVO> listNextRoutes();
}
