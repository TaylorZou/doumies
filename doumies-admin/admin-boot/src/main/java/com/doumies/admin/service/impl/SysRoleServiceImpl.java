package com.doumies.admin.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.admin.mapper.SysRoleMapper;
import com.doumies.admin.pojo.entity.SysRole;
import com.doumies.admin.pojo.entity.SysRoleMenu;
import com.doumies.admin.pojo.entity.SysRolePermission;
import com.doumies.admin.pojo.entity.SysUserRole;
import com.doumies.admin.service.ISysRoleMenuService;
import com.doumies.admin.service.ISysRolePermissionService;
import com.doumies.admin.service.ISysRoleService;
import com.doumies.admin.service.ISysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private ISysRoleMenuService iSysRoleMenuService;
    private ISysUserRoleService iSysUserRoleService;
    private ISysRolePermissionService iSysRolePermissionService;


    @Override
    public boolean delete(List<Long> ids) {
        Optional.ofNullable(ids).orElse(new ArrayList<>()).forEach(id -> {
            int count = iSysUserRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
            Assert.isTrue(count <= 0, "该角色已分配用户，无法删除");
            iSysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
            iSysRolePermissionService.remove(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, id));
        });
        return this.removeByIds(ids);
    }

}
