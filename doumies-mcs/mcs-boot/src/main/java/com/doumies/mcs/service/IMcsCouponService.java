package com.doumies.mcs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.mcs.pojo.domain.McsCoupon;
import com.doumies.mcs.pojo.form.CouponForm;
import com.doumies.mcs.pojo.query.CouponPageQuery;
import com.doumies.mcs.pojo.vo.CouponTemplateVO;
import com.doumies.mcs.pojo.vo.McsCouponVO;

import java.util.List;

/**
 * @author Taylor
 * @date 2021/3/14
 */
public interface IMcsCouponService extends IService<McsCoupon> {

    /**
     * 查询用户可用优惠券列表
     * @param userId 用户ID
     * @return {@link CouponTemplateVO} 优惠券模板列表
     */
    List<CouponTemplateVO> findAvailableTemplate(Long userId);


    /**
     * 领取优惠券
     *
     * @param userId 用户ID
     * @param templateId 优惠券模板ID
     */
    void receive(Long userId, String templateId);

    /**
     * 根据用户ID和优惠券状态查询优惠券列表
     *
     * @param userId 用户ID
     * @param state  优惠券状态
     * @return {@link McsCouponVO} 优惠券列表
     */
    List<McsCoupon> findCouponsByState(Long userId, Integer state);

    /**
     * 获取优惠券详情
     *
     * @param couponId 优惠券ID
     */
    McsCouponVO detail(String couponId);

    /**
     * 新增优惠券
     *
     * @param form 新增提交表单
     */
    void add(CouponForm form);

    /**
     * 修改优惠券
     *
     * @param form 修改优惠券提交表单
     */
    void modify(CouponForm form);

    IPage<McsCoupon> pageQuery(CouponPageQuery query);

    /**
     * 根据已领券数量
     *
     * @param couponId
     * @return
     */
    int updateTakeStock(String couponId);
}
