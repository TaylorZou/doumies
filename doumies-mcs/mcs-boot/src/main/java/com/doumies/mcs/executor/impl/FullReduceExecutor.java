package com.doumies.mcs.executor.impl;

import cn.hutool.core.collection.CollUtil;
import com.doumies.mcs.executor.AbstractExecutor;
import com.doumies.mcs.executor.RuleExecutor;
import com.doumies.mcs.pojo.enums.RuleFlagEnum;
import com.doumies.mcs.pojo.vo.CouponTemplateVO;
import com.doumies.mcs.pojo.vo.SettlementInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc：满减优惠券结算规则执行器
 * @author Taylor
 * @date 2021/3/14
 */
@Component
@Slf4j
public class FullReduceExecutor extends AbstractExecutor implements RuleExecutor {
    @Override
    public RuleFlagEnum ruleConfig() {
        return RuleFlagEnum.FULLREDUCE;
    }

    @Override
    public SettlementInfoVO computeRule(SettlementInfoVO settlement) {
        Long goodsCostSum = goodsCostSum(settlement.getGoodsInfos());
        // 判断商品类型与优惠券是否满足条件
        SettlementInfoVO probability = processGoodsTypeNotSatisfy(settlement, goodsCostSum);
        if (probability != null) {
            log.debug("Manjian Coupon Is Not Match To GoodsType.");
            return probability;
        }
        CouponTemplateVO template = settlement.getCouponAndTemplateInfos().get(0).getTemplate();
        Long base = template.getRule().getDiscount().getBase();
        Long quota = template.getRule().getDiscount().getQuota();
        if (goodsCostSum < base) {
            log.debug("Current Goods Cost Sum < Manjian Coupon Base");
            settlement.setPay(goodsCostSum);
            settlement.setCouponAndTemplateInfos(CollUtil.empty(List.class));
            return settlement;
        }
        //计算使用优惠券之后的价格
        settlement.setPay((goodsCostSum - quota) > minCost() ? (goodsCostSum - quota) : minCost());
        log.debug("Use ManJian Coupon Make Goods Cost From {} To {}",goodsCostSum,settlement.getPay());
        return settlement;
    }
}