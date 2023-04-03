package com.doumies.pcs.serviceapp.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.common.web.exception.BizException;
import com.doumies.pcs.common.enums.AttributeTypeEnum;
import com.doumies.pcs.mapper.PcsSpuMapper;
import com.doumies.pcs.pojo.entity.PcsSku;
import com.doumies.pcs.pojo.entity.PcsSpu;
import com.doumies.pcs.pojo.entity.PcsSpuAttributeValue;
import com.doumies.pcs.pojo.vo.ProductHistoryVO;
import com.doumies.pcs.pojo.vo.app.GoodsDetailVO;
import com.doumies.pcs.service.IPcsSkuService;
import com.doumies.pcs.service.IPcsSpuAttributeValueService;
import com.doumies.pcs.serviceapp.IGoodsService;
import com.doumies.ucs.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Taylor
 * @date 2023/2/28
 */
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<PcsSpuMapper, PcsSpu> implements IGoodsService {

    final IPcsSpuAttributeValueService spuAttributeValueService;
    final IPcsSkuService skuService;
    final MemberFeignClient memberFeignClient;

    @Override
    public GoodsDetailVO getGoodsById(Long goodsId) {

        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        PcsSpu pcsSpu = this.baseMapper.selectById(goodsId);
        Assert.isTrue(pcsSpu != null, "商品不存在");
        // 商品基本信息
        GoodsDetailVO.GoodsInfo goodsInfo = new GoodsDetailVO.GoodsInfo();
        BeanUtil.copyProperties(pcsSpu, goodsInfo, "album");

        List<String> album = new ArrayList<>();

        if (StrUtil.isNotBlank(pcsSpu.getPicUrl())) {
            album.add(pcsSpu.getPicUrl());
        }
        if (pcsSpu.getAlbum() != null && pcsSpu.getAlbum().length > 0) {
            album.addAll(Arrays.asList(pcsSpu.getAlbum()));
            goodsInfo.setAlbum(album);
        }
        goodsDetailVO.setGoodsInfo(goodsInfo);

        // 商品属性列表
        List<GoodsDetailVO.Attribute> attributeList = spuAttributeValueService.list(new LambdaQueryWrapper<PcsSpuAttributeValue>()
                .eq(PcsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
                .eq(PcsSpuAttributeValue::getSpuId, goodsId)
                .select(PcsSpuAttributeValue::getId, PcsSpuAttributeValue::getName, PcsSpuAttributeValue::getValue)
        ).stream().map(item -> {
            GoodsDetailVO.Attribute attribute = new GoodsDetailVO.Attribute();
            BeanUtil.copyProperties(item, attribute);
            return attribute;
        }).collect(Collectors.toList());
        goodsDetailVO.setAttributeList(attributeList);


        // 商品规格列表
        List<PcsSpuAttributeValue> specSourceList = spuAttributeValueService.list(new LambdaQueryWrapper<PcsSpuAttributeValue>()
                .eq(PcsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
                .eq(PcsSpuAttributeValue::getSpuId, goodsId)
                .select(PcsSpuAttributeValue::getId, PcsSpuAttributeValue::getName, PcsSpuAttributeValue::getValue)
        );

        List<GoodsDetailVO.Specification> specList = new ArrayList<>();
        // 规格Map [key:"颜色",value:[{id:1,value:"黑"},{id:2,value:"白"}]]
        Map<String, List<PcsSpuAttributeValue>> specValueMap = specSourceList.stream()
                .collect(Collectors.groupingBy(PcsSpuAttributeValue::getName));

        for (Map.Entry<String, List<PcsSpuAttributeValue>> entry : specValueMap.entrySet()) {
            String specName = entry.getKey();
            List<PcsSpuAttributeValue> specValueSourceList = entry.getValue();

            // 规格映射处理
            GoodsDetailVO.Specification spec = new GoodsDetailVO.Specification();
            spec.setName(specName);
            if (CollectionUtil.isNotEmpty(specValueSourceList)) {
                List<GoodsDetailVO.Specification.Value> specValueList = specValueSourceList.stream().map(item -> {
                    GoodsDetailVO.Specification.Value specValue = new GoodsDetailVO.Specification.Value();
                    specValue.setId(item.getId());
                    specValue.setValue(item.getValue());
                    return specValue;
                }).collect(Collectors.toList());
                spec.setValues(specValueList);
                specList.add(spec);
            }
        }
        goodsDetailVO.setSpecList(specList);
        // 商品SKU列表
        List<PcsSku> skuSourceList = skuService.list(new LambdaQueryWrapper<PcsSku>().eq(PcsSku::getSpuId, goodsId));
        if (CollectionUtil.isNotEmpty(skuSourceList)) {
            List<GoodsDetailVO.Sku> skuList = skuSourceList.stream().map(item -> {
                GoodsDetailVO.Sku sku = new GoodsDetailVO.Sku();
                BeanUtil.copyProperties(item, sku);
                return sku;
            }).collect(Collectors.toList());
            goodsDetailVO.setSkuList(skuList);
        }
        // 添加用户浏览历史记录
        ProductHistoryVO vo = new ProductHistoryVO();
        vo.setId(goodsInfo.getId());
        vo.setName(goodsInfo.getName());
        vo.setPicUrl(goodsInfo.getAlbum() != null ? goodsInfo.getAlbum().get(0) : null);
        memberFeignClient.addProductViewHistory(vo);
        return goodsDetailVO;
    }

    @Override
    public GoodsDetailVO getGoodsBySkuId(Long skuId) {
        PcsSku skuInfo = skuService.getById(skuId);
        if (null == skuInfo) {
            throw new BizException("商品不存在");
        }
        Long spuId = skuInfo.getSpuId();
        return getGoodsById(spuId);
    }
}
