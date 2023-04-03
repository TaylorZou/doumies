package com.doumies.mcs.executor;

import com.doumies.common.web.exception.BizException;
import com.doumies.mcs.pojo.enums.CouponCategoryEnum;
import com.doumies.mcs.pojo.enums.RuleFlagEnum;
import com.doumies.mcs.pojo.vo.SettlementInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Taylor
 * @date 2021/3/14
 * @desc: 优惠券结算规则执行管理器
 */
@Slf4j
@Component
public class ExecutorManager implements BeanPostProcessor {

    /**
     * 规则执行器映射
     */
    private static Map<RuleFlagEnum, RuleExecutor> executorIndex = new HashMap<>(RuleFlagEnum.values().length);

    public SettlementInfoVO computeRule(SettlementInfoVO settlement){
        SettlementInfoVO result = null;

        // 单类优惠券
        if (settlement.getCouponAndTemplateInfos().size() == 1){
            CouponCategoryEnum category = settlement.getCouponAndTemplateInfos().get(0).getTemplate().getCategory();
            switch (category){
                case FULLREDUCE:
                    settlement = executorIndex.get(RuleFlagEnum.FULLREDUCE).computeRule(settlement);
                    break;
                case IMMEDIATEREDUCE:
                    settlement = executorIndex.get(RuleFlagEnum.IMMEDIATEREDUCE).computeRule(settlement);
                    break;
                case DISCOUNT:
                    settlement = executorIndex.get(RuleFlagEnum.DISCOUNT).computeRule(settlement);
                    break;
            }
        }else {
            List<CouponCategoryEnum> categories = settlement.getCouponAndTemplateInfos().stream().map(obj -> {
                return obj.getTemplate().getCategory();
            }).collect(Collectors.toList());
            if (categories.size() > 2){
                throw new BizException("Not Support Form More Template Category");
            }
            if (categories.contains(CouponCategoryEnum.FULLREDUCE) && categories.contains(CouponCategoryEnum.DISCOUNT)){
                settlement = executorIndex.get(RuleFlagEnum.FULLREDUCE_DISCOUNT).computeRule(settlement);
            }else {
                throw new BizException("Not Support Form Other Template Category");
            }
        }

        return result;
    }



    /**
     * 在bean初始化之前去执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (!(bean instanceof RuleExecutor)) {
            return bean;
        }
        RuleExecutor executor = (RuleExecutor) bean;
        RuleFlagEnum ruleFlag = executor.ruleConfig();
        if (executorIndex.containsKey(ruleFlag)) {
            throw new IllegalStateException("There is already an executor for rule flag: " + ruleFlag);
        }
        log.info("Load executor {} for rule flag {}.", executor.getClass(), ruleFlag);
        executorIndex.put(ruleFlag, executor);
        return bean;
    }

    /**
     * 在bean初始化之后去执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
