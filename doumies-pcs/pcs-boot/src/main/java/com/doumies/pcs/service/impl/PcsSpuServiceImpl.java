package com.doumies.pcs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.pcs.common.constant.PcsConstants;
import com.doumies.pcs.common.enums.AttributeTypeEnum;
import com.doumies.pcs.mapper.PcsSpuMapper;
import com.doumies.pcs.pojo.dto.admin.GoodsFormDTO;
import com.doumies.pcs.pojo.entity.PcsSku;
import com.doumies.pcs.pojo.entity.PcsSpu;
import com.doumies.pcs.pojo.entity.PcsSpuAttributeValue;
import com.doumies.pcs.pojo.vo.admin.GoodsDetailVO;
import com.doumies.pcs.service.IPcsSkuService;
import com.doumies.pcs.service.IPcsSpuAttributeValueService;
import com.doumies.pcs.service.IPcsSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Taylor
 * @date 2023/02/28
 */
@Service
@RequiredArgsConstructor
public class PcsSpuServiceImpl extends ServiceImpl<PcsSpuMapper, PcsSpu> implements IPcsSpuService {
    private final IPcsSkuService iPcsSkuService;
    private final IPcsSpuAttributeValueService iPcsSpuAttributeValueService;

    /**
     * 商品分页列表
     *
     * @param page
     * @param name
     * @param categoryId
     * @return
     */
    @Override
    public IPage<PcsSpu> list(Page<PcsSpu> page, String name, Long categoryId) {
        List<PcsSpu> list = this.baseMapper.list(page, name, categoryId);
        page.setRecords(list);
        return page;
    }

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    @Override
    @Transactional
    public boolean addGoods(GoodsFormDTO goods) {
        Long goodsId = this.saveSpu(goods);
        // 属性保存
        List<GoodsFormDTO.AttributeValue> attrValList = goods.getAttrList();
        this.saveAttribute(goodsId, attrValList);
        // 规格保存
        List<GoodsFormDTO.AttributeValue> specList = goods.getSpecList();
        Map<String, Long> specTempIdIdMap = this.saveSpecification(goodsId, specList);
        // SKU保存
        List<PcsSku> skuList = goods.getSkuList();
        return this.saveSku(goodsId, skuList, specTempIdIdMap);
    }


    /**
     * 修改商品
     *
     * @param goods
     * @return
     */
    @Transactional
    @Override
    public boolean updateGoods(GoodsFormDTO goods) {

        Long goodsId = goods.getId();
        // 属性保存
        List<GoodsFormDTO.AttributeValue> attrValList = goods.getAttrList();
        this.saveAttribute(goodsId, attrValList);

        // 规格保存
        List<GoodsFormDTO.AttributeValue> specList = goods.getSpecList();
        Map<String, Long> specTempIdIdMap = this.saveSpecification(goodsId, specList);

        // SKU保存
        List<PcsSku> skuList = goods.getSkuList();
        this.saveSku(goodsId, skuList, specTempIdIdMap);

        // SPU
        boolean saveResult = this.saveSpu(goods) > 0;
        return saveResult;
    }

    /**
     * 获取商品（SPU）详情
     *
     * @param id 商品（SPU）ID
     * @return
     */
    @Override
    public GoodsDetailVO getGoodsById(Long id) {
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();

        PcsSpu spu = this.getById(id);
        Assert.isTrue(spu != null, "商品不存在");

        BeanUtil.copyProperties(spu, goodsDetailVO);

        // 商品属性列表
        List<PcsSpuAttributeValue> attrList = iPcsSpuAttributeValueService.list(new LambdaQueryWrapper<PcsSpuAttributeValue>()
                .eq(PcsSpuAttributeValue::getSpuId, id)
                .eq(PcsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
        );
        goodsDetailVO.setAttrList(attrList);

        // 商品规格列表
        List<PcsSpuAttributeValue> specList = iPcsSpuAttributeValueService.list(new LambdaQueryWrapper<PcsSpuAttributeValue>()
                .eq(PcsSpuAttributeValue::getSpuId, id)
                .eq(PcsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
        );
        goodsDetailVO.setSpecList(specList);

        // 商品SKU列表
        List<PcsSku> skuList = iPcsSkuService.list(new LambdaQueryWrapper<PcsSku>().eq(PcsSku::getSpuId, id));
        goodsDetailVO.setSkuList(skuList);
        return goodsDetailVO;

    }


    /**
     * 批量删除商品（SPU）
     *
     * @param goodsIds
     * @return
     */
    @Override
    @Transactional
    public boolean removeByGoodsIds(List<Long> goodsIds) {
        boolean result = true;
        for (Long goodsId : goodsIds) {
            // sku
            iPcsSkuService.remove(new LambdaQueryWrapper<PcsSku>().eq(PcsSku::getSpuId, goodsId));
            // 规格
            iPcsSpuAttributeValueService.remove(new LambdaQueryWrapper<PcsSpuAttributeValue>().eq(PcsSpuAttributeValue::getId, goodsId));
            // 属性
            iPcsSpuAttributeValueService.remove(new LambdaQueryWrapper<PcsSpuAttributeValue>().eq(PcsSpuAttributeValue::getSpuId, goodsId));
            // spu
            result = this.removeById(goodsId);
        }
        return result;
    }


    /**
     * 保存商品
     *
     * @param goods
     * @return
     */
    private Long saveSpu(GoodsFormDTO goods) {
        PcsSpu pcsSpu = new PcsSpu();
        BeanUtil.copyProperties(goods, pcsSpu);
        // 商品图册
        pcsSpu.setAlbum(goods.getSubPicUrls());
        boolean result = this.saveOrUpdate(pcsSpu);
        return result ? pcsSpu.getId() : 0;
    }


    /**
     * 保存SKU，需要替换提交表单中的临时规格ID
     *
     * @param goodsId
     * @param skuList
     * @param specTempIdIdMap
     * @return
     */
    private boolean saveSku(Long goodsId, List<PcsSku> skuList, Map<String, Long> specTempIdIdMap) {

        // 删除SKU
        List<Long> formSkuIds = skuList.stream().map(PcsSku::getId).collect(Collectors.toList());

        List<Long> dbSkuIds = iPcsSkuService.list(new LambdaQueryWrapper<PcsSku>()
                .eq(PcsSku::getSpuId, goodsId)
                .select(PcsSku::getId))
                .stream().map(PcsSku::getId)
                .collect(Collectors.toList());

        List<Long> removeSkuIds = dbSkuIds.stream().filter(dbSkuId -> !formSkuIds.contains(dbSkuId)).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeSkuIds)) {
            iPcsSkuService.removeByIds(removeSkuIds);
        }

        // 新增/修改SKU
        List<PcsSku> pcsSkuList = skuList.stream().map(sku -> {
            // 临时规格ID转换
            String specIds = Arrays.stream(sku.getSpecIds().split("\\|"))
                    .map(specId ->
                            specId.startsWith(PcsConstants.TEMP_ID_PREFIX) ? specTempIdIdMap.get(specId) + "" : specId
                    )
                    .collect(Collectors.joining("_"));
            sku.setSpecIds(specIds);
            sku.setSpuId(goodsId);
            return sku;
        }).collect(Collectors.toList());
        return iPcsSkuService.saveOrUpdateBatch(pcsSkuList);
    }


    /**
     * 保存商品属性
     *
     * @param goodsId
     * @param attrValList
     * @return
     */
    private boolean saveAttribute(Long goodsId, List<GoodsFormDTO.AttributeValue> attrValList) {
        List<Long> formAttrValIds = attrValList.stream()
                .filter(item -> item.getId() != null)
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());

        List<Long> dbAttrValIds = iPcsSpuAttributeValueService.list(new LambdaQueryWrapper<PcsSpuAttributeValue>()
                .eq(PcsSpuAttributeValue::getSpuId, goodsId)
                .eq(PcsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
                .select(PcsSpuAttributeValue::getId)
        ).stream().map(PcsSpuAttributeValue::getId).collect(Collectors.toList());

        List<Long> removeAttrValIds = dbAttrValIds.stream().filter(id -> !formAttrValIds.contains(id)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            iPcsSpuAttributeValueService.removeByIds(removeAttrValIds);
        }

        // 新增或修改商品属性
        List<PcsSpuAttributeValue> pcsSpuAttributeValueList = attrValList.stream().map(item -> {
            PcsSpuAttributeValue pcsSpuAttributeValue = new PcsSpuAttributeValue();
            BeanUtil.copyProperties(item, pcsSpuAttributeValue);
            pcsSpuAttributeValue.setSpuId(goodsId);
            pcsSpuAttributeValue.setType(AttributeTypeEnum.ATTRIBUTE.getValue());
            return pcsSpuAttributeValue;
        }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(pcsSpuAttributeValueList)) {
            return iPcsSpuAttributeValueService.saveOrUpdateBatch(pcsSpuAttributeValueList);
        }
        return true;
    }

    /**
     * 保存商品规格，新增的规格需要返回临时ID和持久化到数据库的ID的映射关系，替换SKU中的规格ID集合
     *
     * @param goodsId  商品ID
     * @param specList 规格列表
     * @return Map: key-临时ID；value-持久化返回ID
     */
    private Map<String, Long> saveSpecification(Long goodsId, List<GoodsFormDTO.AttributeValue> specList) {

        // 删除规格
        List<Long> formSpecValIds = specList.stream()
                .filter(item -> item.getId() != null && !item.getId().startsWith(PcsConstants.TEMP_ID_PREFIX))
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());

        List<Long> dbSpecValIds = iPcsSpuAttributeValueService.list(new LambdaQueryWrapper<PcsSpuAttributeValue>()
                .eq(PcsSpuAttributeValue::getSpuId, goodsId)
                .eq(PcsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
                .select(PcsSpuAttributeValue::getId)
        ).stream().map(PcsSpuAttributeValue::getId).collect(Collectors.toList());

        List<Long> removeAttrValIds = dbSpecValIds.stream().filter(id -> !formSpecValIds.contains(id)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            iPcsSpuAttributeValueService.removeByIds(removeAttrValIds);
        }
        // 新增规格
        Map<String, Long> tempIdIdMap = new HashMap<>();
        List<GoodsFormDTO.AttributeValue> newSpecList = specList.stream()
                .filter(item -> item.getId().startsWith(PcsConstants.TEMP_ID_PREFIX)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(newSpecList)) {
            newSpecList.forEach(item -> {
                PcsSpuAttributeValue specification = new PcsSpuAttributeValue();
                BeanUtil.copyProperties(item, specification, "id");
                specification.setSpuId(goodsId);
                specification.setType(AttributeTypeEnum.SPECIFICATION.getValue());
                iPcsSpuAttributeValueService.save(specification);
                tempIdIdMap.put(item.getId(), specification.getId());
            });
        }
        // 修改规格
        List<PcsSpuAttributeValue> pcsSpuAttributeValueList = specList.stream()
                .filter(item -> !item.getId().startsWith(PcsConstants.TEMP_ID_PREFIX))
                .map(spec -> {
                    PcsSpuAttributeValue pcsSpuAttributeValue = new PcsSpuAttributeValue();
                    BeanUtil.copyProperties(spec, pcsSpuAttributeValue);
                    pcsSpuAttributeValue.setSpuId(goodsId);
                    pcsSpuAttributeValue.setType(AttributeTypeEnum.SPECIFICATION.getValue());
                    return pcsSpuAttributeValue;
                }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(pcsSpuAttributeValueList)) {
            iPcsSpuAttributeValueService.updateBatchById(pcsSpuAttributeValueList);
        }
        return tempIdIdMap;
    }
}
