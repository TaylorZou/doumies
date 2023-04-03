package com.doumies.mcs.pojo.dto;

import com.doumies.mcs.pojo.vo.TemplateRuleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @desc：微服务之间调用的优惠券模板信息
 * @author Taylor
 * @date 2021/3/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateDTO {

    /**
     * 优惠券模板主键ID
     */
    private Long id;

    /**
     * 优惠券模板名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 分类
     */
    private String category;

    /**
     * 产品线
     */
    private String productLine;

    /**
     * 优惠券模板编码
     */
    private String code;

    /**
     * 目标用户
     */
    private Integer target;

    /**
     * 优惠券规则
     */
    private TemplateRuleVO rule;


}
