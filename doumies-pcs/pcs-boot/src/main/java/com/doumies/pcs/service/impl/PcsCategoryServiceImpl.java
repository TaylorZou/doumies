package com.doumies.pcs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.common.constant.GlobalConstants;
import com.doumies.common.domain.ValueLabel;
import com.doumies.pcs.mapper.PcsCategoryMapper;
import com.doumies.pcs.pojo.entity.PcsCategory;
import com.doumies.pcs.pojo.vo.CategoryVO;
import com.doumies.pcs.service.IPcsCategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 商品分类
 *
 * @author Taylor
 */
@Service
public class PcsCategoryServiceImpl extends ServiceImpl<PcsCategoryMapper, PcsCategory> implements IPcsCategoryService {


    /**
     * 分类列表（树形）
     *
     * @param parentId
     * @return
     * @Cacheable value:缓存名称(分区)；key：缓存键
     */
    // @Cacheable(value = "pms", key = "'categoryList'")
    @Override
    public List<CategoryVO> listCategory(Long parentId) {
        List<PcsCategory> categoryList = this.list(
                new LambdaQueryWrapper<PcsCategory>()
                        .eq(PcsCategory::getVisible, GlobalConstants.STATUS_YES)
                        .orderByDesc(PcsCategory::getSort)
        );
        List<CategoryVO> list = recursionTree(parentId != null ? parentId : 0l, categoryList);
        return list;
    }


    private static List<CategoryVO> recursionTree(Long parentId, List<PcsCategory> categoryList) {
        List<CategoryVO> list = new ArrayList<>();
        Optional.ofNullable(categoryList)
                .ifPresent(categories ->
                        categories.stream().filter(category ->
                                category.getParentId().equals(parentId))
                                .forEach(category -> {
                                    CategoryVO categoryVO = new CategoryVO();
                                    BeanUtil.copyProperties(category, categoryVO);
                                    List<CategoryVO> children = recursionTree(category.getId(), categoryList);
                                    categoryVO.setChildren(children);
                                    list.add(categoryVO);
                                }));
        return list;
    }


    /**
     * 分类列表（级联）
     *
     * @return
     */
    @Override
    public List<ValueLabel> listCascadeCategory() {
        List<PcsCategory> categoryList = this.list(
                new LambdaQueryWrapper<PcsCategory>()
                        .eq(PcsCategory::getVisible, GlobalConstants.STATUS_YES)
                        .orderByAsc(PcsCategory::getSort)
        );
        List<ValueLabel> list = recursionCascade(0l, categoryList);
        return list;
    }

    private List<ValueLabel> recursionCascade(Long parentId, List<PcsCategory> categoryList) {
        List<ValueLabel> list = new ArrayList<>();
        Optional.ofNullable(categoryList)
                .ifPresent(categories ->
                        categories.stream().filter(category ->
                                category.getParentId().equals(parentId))
                                .forEach(category -> {
                                    ValueLabel categoryVO = new ValueLabel()
                                            .setLabel(category.getName())
                                            .setValue(category.getId());
                                    BeanUtil.copyProperties(category, categoryVO);
                                    List<ValueLabel> children = recursionCascade(category.getId(), categoryList);
                                    categoryVO.setChildren(children);
                                    list.add(categoryVO);
                                })
                );
        return list;
    }


    /**
     * 新增/修改分类
     *
     * @param category
     * @return 分类ID
     * @CacheEvict 缓存失效
     */
    @CacheEvict(value = "com/doumies/pcs", key = "'categoryList'")
    @Override
    public Long saveCategory(PcsCategory category) {
        this.saveOrUpdate(category);
        return category.getId();
    }
}
