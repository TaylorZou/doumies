package com.doumies.mcs.pojo.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mcs_seckill_sku_relation
 * @author Taylor
 * @date 2021/3/10
 */
@Data
public class McsSeckillSkuRelation implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 活动场次id
     */
    private Long sessionId;

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 秒杀价格
     */
    private Long seckillPrice;

    /**
     * 秒杀总量
     */
    private Integer seckillCount;

    /**
     * 每人限购数量
     */
    private Integer seckillLimit;

    /**
     * 排序
     */
    private Integer seckillSort;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}