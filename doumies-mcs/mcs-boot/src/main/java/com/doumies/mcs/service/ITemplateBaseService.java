package com.doumies.mcs.service;

import com.doumies.mcs.pojo.domain.McsCouponTemplate;
import com.doumies.mcs.pojo.vo.CouponTemplateVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @desc: 优惠券模板基础业务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface ITemplateBaseService {

    /**
     * 根据优惠券模板id 获取优惠券模板信息
     * @param id 优惠券模板ID
     * @return {@link McsCouponTemplate} 优惠券模板实体类
     */
    CouponTemplateVO queryTemplateInfo(Long id);

    /**
     * 查询所有可用优惠券模板列表
     * @return 优惠券模板列表
     */
    List<McsCouponTemplate> findAllUsableTemplate();

    /**
     * 根据优惠券模板ID集合查询可用模板列表
     * @param ids 优惠券模板ID集合
     * @return 优惠券模板列表
     */
    Map<Long, McsCouponTemplate> findAllTemplateByIds(Collection<Long> ids);


}
