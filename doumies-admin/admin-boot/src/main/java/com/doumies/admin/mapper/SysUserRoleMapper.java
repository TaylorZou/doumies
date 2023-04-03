package com.doumies.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.admin.pojo.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色持久层
 *
 * @author Taylor
 * @date 2023/2/25
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
