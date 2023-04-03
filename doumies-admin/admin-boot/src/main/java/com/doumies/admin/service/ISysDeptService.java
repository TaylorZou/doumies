package com.doumies.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.admin.pojo.entity.SysDept;
import com.doumies.admin.pojo.vo.IdLabelVO;
import com.doumies.admin.pojo.vo.dept.DeptVO;

import java.util.List;

/**
 * 菜单控制器
 *
 * @author Taylor
 * @date 2023-2-25
 */
public interface ISysDeptService extends IService<SysDept> {
    /**
     * 部门表格（Table）层级列表
     *
     * @param status 部门状态： 1-开启 0-禁用
     * @param name
     * @return
     */
    List<DeptVO> listTable(Integer status, String name);

    /**
     * 部门树形下拉（TreeSelect）层级列表
     *
     * @return
     */
    List<IdLabelVO> listTreeSelect();

    /**
     * 新增/修改部门
     *
     * @param dept
     * @return
     */
    Long saveDept(SysDept dept);

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return
     */
    boolean deleteByIds(String ids);
}
