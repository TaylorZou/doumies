package com.doumies.mcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.mcs.pojo.domain.McsCouponRecord;

/**
 * @desc 优惠券领券记录业务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface ICouponRecordService extends IService<McsCouponRecord> {

    /**
     * 用户领券
     * @param couponId
     */
    void add(String couponId);
}
