package com.doumies.mcs.executor;

import com.doumies.mcs.pojo.enums.RuleFlagEnum;
import com.doumies.mcs.pojo.vo.SettlementInfoVO;

/**
 * @desc: 优惠券模板规则处理器接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface RuleExecutor {

    /**
     * 规则类型标记
     * @return {@RuleFlagEnum}
     */
    RuleFlagEnum ruleConfig();

    /**
     * 优惠券规则计算
     * @param settlement
     * @return
     */
    SettlementInfoVO computeRule(SettlementInfoVO settlement);
}
