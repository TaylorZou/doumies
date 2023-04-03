package com.doumies.admin.mapper;

/**
 * @author Taylor
 * @date 2023/2/24 21:52
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.admin.pojo.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author Taylor
 * @date 2023-2-25
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listRoutes();

}
