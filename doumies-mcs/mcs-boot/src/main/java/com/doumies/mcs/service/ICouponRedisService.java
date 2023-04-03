package com.doumies.mcs.service;

import com.doumies.mcs.pojo.domain.McsCoupon;

import java.util.List;

/**
 * @desc: 优惠券 Cache 业务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface ICouponRedisService {

    /**
     * 从缓存中获取用户优惠券列表
     * @param userId 用户ID
     * @param state 优惠券状态
     * @return 优惠券列表
     */
    List<McsCoupon> getCachedCoupons(Long userId, Integer state);

    /**
     * 保存空的优惠券列表到缓存中
     * 目的：避免缓存穿透
     * @param userId 用户ID
     * @param states 优惠券状态列表
     */
    void saveEmptyCouponListToCache(Long userId, List<Integer> states);

    /**
     * 尝试从 Cache 中获取一个优惠券码
     * @param templateId 优惠券模板ID
     * @return 优惠券码
     */
    String tryToAcquireCouponCodeFromCache(String templateId);

    /**
     * 向缓存中添加用户可用优惠券
     * @param userId 用户ID
     * @param coupons 优惠券列表
     * @param state 状态
     * @return
     */
    Integer addCouponToCache(Long userId, List<McsCoupon> coupons, Integer state);
}
