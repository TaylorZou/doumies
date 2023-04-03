package com.doumies.mcs.service;

import com.doumies.mcs.pojo.vo.McsSeckillSkuVO;

import java.util.List;

/**
 * @desc 秒杀模块业务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface ISeckillService {

    void updateSeckillSkuLatest3Days();

    /**
     * 获取当前时间秒杀活动商品列表
     * @return
     */
    List<McsSeckillSkuVO> getCurrentSeckillSession();

}
