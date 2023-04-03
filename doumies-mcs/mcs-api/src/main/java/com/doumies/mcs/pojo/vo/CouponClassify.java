package com.doumies.mcs.pojo.vo;

import cn.hutool.core.date.DateUtil;
import com.doumies.mcs.pojo.domain.McsCoupon;
import com.doumies.mcs.pojo.domain.McsCouponTemplate;
import com.doumies.mcs.pojo.enums.CouponStateEnum;
import com.doumies.mcs.pojo.enums.PeriodTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @desc: 优惠券状态分类
 * @author Taylor
 * @date 2021/3/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponClassify {

    /**
     * 可用优惠券
     */
    private List<McsCoupon> usable;

    /**
     * 已使用优惠券
     */
    private List<McsCoupon> used;

    /**
     * 已过期优惠券
     */
    private List<McsCoupon> expired;

    public static CouponClassify classify(List<McsCoupon> coupons) {
        List<McsCoupon> usable = new ArrayList<>(coupons.size());
        List<McsCoupon> used = new ArrayList<>(coupons.size());
        List<McsCoupon> expired = new ArrayList<>(coupons.size());
        Date time = new Date();

        coupons.forEach(coupon -> {
            boolean isTimeExpire = false;
            McsCouponTemplate template = coupon.getTemplate();
            // 判断优惠券是否过期
            if (template.getRule().getExpiration().getPeriod().equals(PeriodTypeEnum.REGULAR.getCode())) {
//                isTimeExpire = template.getRule().getExpiration().getDeadline() <= time.getTime();
            } else {
                isTimeExpire = DateUtil.offsetDay(coupon.getGmtCreate(), template.getRule().getExpiration().getGap()).getTime() <= time.getTime();
            }

            if (coupon.getState().equals(CouponStateEnum.USED)) {
                used.add(coupon);
            } else if (coupon.getState().equals(CouponStateEnum.EXPIRED) || isTimeExpire) {
                coupon.setState(CouponStateEnum.EXPIRED);
                expired.add(coupon);
            } else {
                usable.add(coupon);
            }

        });
        return new CouponClassify(usable, used, expired);
    }
}
