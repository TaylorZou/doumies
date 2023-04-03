package com.doumies.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.common.domain.ValueLabel;
import com.doumies.pcs.pojo.entity.PcsCategory;
import com.doumies.pcs.pojo.vo.CategoryVO;

import java.util.List;


/**
 * 商品分类
 *
 * @author Taylor
 */
public interface IPcsCategoryService extends IService<PcsCategory> {


    /**
     * 分类列表（树形）
     *
     * @param parentId
     * @return
     */
    List<CategoryVO> listCategory(Long parentId);

    /**
     * 分类列表（级联）
     * @return
     */
    List<ValueLabel> listCascadeCategory();


    /**
     *
     * 保存（新增/修改）分类
     *
     *
     * @param category
     * @return
     */
    Long saveCategory(PcsCategory category);

}
