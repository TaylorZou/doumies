package com.doumies.mcs.executor.impl;

import cn.hutool.core.collection.CollUtil;
import com.doumies.mcs.executor.AbstractExecutor;
import com.doumies.mcs.executor.RuleExecutor;
import com.doumies.mcs.pojo.enums.CouponCategoryEnum;
import com.doumies.mcs.pojo.enums.RuleFlagEnum;
import com.doumies.mcs.pojo.vo.SettlementInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc: 满减 + 折扣优惠券结算规则执行器
 * @author Taylor
 * @date 2021/3/14
 */
@Slf4j
@Component
public class FullReduceAndDiscountExecutor extends AbstractExecutor implements RuleExecutor {
    @Override
    public RuleFlagEnum ruleConfig() {
        return RuleFlagEnum.FULLREDUCE_DISCOUNT;
    }

    @Override
    protected boolean isGoodsTypeSatisfy(SettlementInfoVO settlement) {
        log.debug("Check ManJian And ZheKou Is Match Or Not.");
        List<String> goodsType = settlement.getGoodsInfos().stream().map(SettlementInfoVO.GoodsInfo::getType).collect(Collectors.toList());
        List<String> templateGoodsType = new ArrayList<>();

        for (SettlementInfoVO.CouponAndTemplateInfo couponAndTemplateInfo : settlement.getCouponAndTemplateInfos()) {
            List<String> type = couponAndTemplateInfo.getTemplate().getRule().getGoodsCategories();
            templateGoodsType.addAll(type);
        }

        // 如果想要使用多品类优惠券，则必须要所有的商品类型都包含在内，即差集为空
        return CollectionUtils.isEmpty(CollectionUtils.subtract(goodsType, templateGoodsType));

    }

    @Override
    public SettlementInfoVO computeRule(SettlementInfoVO settlement) {
        long goodsCostSum = settlement.getGoodsInfos().stream().mapToLong(g -> g.getCount() * g.getPrice()).sum();
        SettlementInfoVO probaility = processGoodsTypeNotSatisfy(settlement, goodsCostSum);
        if (probaility != null) {
            log.debug("Manjian And ZheKou Coupon Is Not Match To GoodsType.");
            return probaility;
        }

        SettlementInfoVO.CouponAndTemplateInfo manJian = null;
        SettlementInfoVO.CouponAndTemplateInfo zheKou = null;

        for (SettlementInfoVO.CouponAndTemplateInfo couponAndTemplateInfo : settlement.getCouponAndTemplateInfos()) {

            if (couponAndTemplateInfo.getTemplate().getCategory() == CouponCategoryEnum.FULLREDUCE) {
                manJian = couponAndTemplateInfo;
            } else {
                zheKou = couponAndTemplateInfo;
            }
        }

        assert null != manJian;
        assert null != zheKou;

        if (!isTemplateCanShared(manJian, zheKou)) {
            settlement.setPay(goodsCostSum);
            settlement.setCouponAndTemplateInfos(CollUtil.empty(List.class));
            return settlement;
        }

        List<SettlementInfoVO.CouponAndTemplateInfo> ctInfos = new ArrayList<>();
        Long manJianBase = manJian.getTemplate().getRule().getDiscount().getBase();
        Long manJianQuota = manJian.getTemplate().getRule().getDiscount().getQuota();

        Long pay = goodsCostSum;
        if (goodsCostSum >= manJianBase) {
            pay -= manJianQuota;
            ctInfos.add(manJian);
        }

        Long zheKouBase = zheKou.getTemplate().getRule().getDiscount().getBase();
        double zheKouQuota = zheKou.getTemplate().getRule().getDiscount().getQuota();
        if (goodsCostSum >= zheKouBase) {
            pay = Long.parseLong(goodsCostSum * (zheKouQuota * 1.0 / 100) + "");
            ctInfos.add(manJian);
        }
        //计算使用优惠券之后的价格
        settlement.setPay(pay > minCost() ? pay : minCost());
        settlement.setCouponAndTemplateInfos(ctInfos);
        log.debug("Use ManJian And ZheKou Coupon Make Goods Cost From {} To {}", goodsCostSum, settlement.getPay());
        return settlement;
    }


}
