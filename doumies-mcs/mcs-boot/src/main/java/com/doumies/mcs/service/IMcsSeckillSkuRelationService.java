package com.doumies.mcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.mcs.pojo.domain.McsSeckillSkuRelation;

import java.util.List;

/**
 * @desc 秒杀活动场次商品关联业务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface IMcsSeckillSkuRelationService extends IService<McsSeckillSkuRelation> {

    /**
     * 根据秒杀活动ID获取关联商品
     * @param sessionId 秒杀活动场次ID
     * @return 关联商品列表
     */
    List<McsSeckillSkuRelation> selectBySessionId(Long sessionId);
}
