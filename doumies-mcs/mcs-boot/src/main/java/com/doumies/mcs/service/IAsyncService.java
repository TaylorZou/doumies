package com.doumies.mcs.service;

import com.doumies.mcs.pojo.domain.McsCouponTemplate;

/**
 * @desc：异步服务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface IAsyncService {

    /**
     * 通过优惠券模板异步的创建优惠券码
     * @param template {@link McsCouponTemplate} 优惠券模板实体
     */
    void asyncConstructCouponByTemplate(McsCouponTemplate template);
}
