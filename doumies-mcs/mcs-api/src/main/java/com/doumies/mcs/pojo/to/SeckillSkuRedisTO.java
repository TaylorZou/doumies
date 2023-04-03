package com.doumies.mcs.pojo.to;

import com.doumies.pcs.pojo.dto.app.SkuDTO;
import lombok.Data;
import lombok.ToString;

/**
 * @desc 秒杀商品Redis存储 TO
 * @author Taylor
 * @date 2021/3/10
 */
@Data
@ToString
public class SeckillSkuRedisTO {
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
     * 秒杀随机码
     */
    private String randomCode;

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
     * 秒杀开始时间
     */
    private Long startTime;

    /**
     * 秒杀结束时间
     */
    private Long endTime;

    /**
     * 秒杀商品详情
     */
    private SkuDTO skuInfo;

}
