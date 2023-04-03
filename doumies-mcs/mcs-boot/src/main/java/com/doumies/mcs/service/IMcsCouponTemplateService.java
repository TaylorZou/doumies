package com.doumies.mcs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.mcs.pojo.domain.McsCouponTemplate;
import com.doumies.mcs.pojo.form.CouponTemplateForm;
import com.doumies.mcs.pojo.vo.McsCouponTemplateInfoVO;
import com.doumies.mcs.pojo.vo.McsCouponTemplateVO;

import java.util.List;
import java.util.Set;

/**
 * @desc: 优惠券模板业务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface IMcsCouponTemplateService extends IService<McsCouponTemplate> {


    IPage<McsCouponTemplateVO> pageQuery(Integer page, Integer limit, String name);

    /**
     * 创建优惠券模板
     *
     * @param form {@link CouponTemplateForm} 模板信息请求对象
     * @return {@link McsCouponTemplate} 优惠券模板实体类
     */
    McsCouponTemplate createTemplate(CouponTemplateForm form);


    /**
     * 修改优惠券模板
     *
     * @param form {@link CouponTemplateForm} 模板信息请求对象
     * @return {@link McsCouponTemplate} 优惠券模板实体类
     */
    McsCouponTemplate updateTemplate(CouponTemplateForm form);


    /**
     * 删除优惠券模板
     *
     * @param id 优惠券模板ID
     */
    void deleteTemplate(String id);


    /**
     * 优惠券模板审核
     *
     * @param id
     */
    void confirmTemplate(String id);

    /**
     * 查询所有可用优惠券模板列表
     *
     * @param available 是否可用
     * @param expired   是否过期
     * @return 优惠券模板
     */
    List<McsCouponTemplate> findAllUsableTemplate(Integer available, Integer expired);

    /**
     * 查询未过期的优惠券模板列表
     *
     * @param verifyStateCode 是否已审核
     * @param usedStateCode   是否在使用有效期范围内
     * @return 优惠券模板列表
     */
    List<McsCouponTemplate> findAllNotExpiredTemplate(Integer verifyStateCode, Integer usedStateCode);

    /**
     * 查询优惠券模板详情
     *
     * @param id 优惠券模板ID
     * @return 优惠券模板详情
     */
    McsCouponTemplateInfoVO info(String id);

    /**
     * 查询所有正在发放中的优惠券模板
     *
     * @param userTypes 用户类型
     * @return 优惠券模板列表
     */
    List<McsCouponTemplate> findAllOfferingTemplate(Set<Integer> userTypes);

    /**
     * 查询优惠券模板
     * @param id
     * @return
     */
    McsCouponTemplate findByTemplateId(String id);
}
