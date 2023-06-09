package com.doumies.mcs.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.doumies.common.redis.utils.RedisUtils;
import com.doumies.mcs.pojo.domain.McsSeckillSession;
import com.doumies.mcs.pojo.domain.McsSeckillSkuRelation;
import com.doumies.mcs.pojo.to.SeckillSkuRedisTO;
import com.doumies.mcs.pojo.vo.McsSeckillSkuVO;
import com.doumies.mcs.service.ISeckillService;
import com.doumies.mcs.service.IMcsSeckillSessionService;
import com.doumies.mcs.service.IMcsSeckillSkuRelationService;
import com.doumies.mcs.util.BeanMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc 秒杀模块业务实现类
 * @author Taylor
 * @date 2021/3/14
 */
@Service
@Slf4j
public class SeckillServiceImpl implements ISeckillService {

    private static final String SECKILL_SESSION_CACHE_PREFIX = "seckill:sessions:";
    private static final String SECKILL_SKU_CACHE_PREFIX = "seckill:skus";
    private static final String SECKILL_SKU_SEMAPHORE = "seckill:stock:";

    @Autowired
    private IMcsSeckillSessionService seckillSessionService;

    @Autowired
    private IMcsSeckillSkuRelationService seckillSkuRelationService;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private RedissonClient redissonClient;


    @Override
    public void updateSeckillSkuLatest3Days() {
        // 1、获取需要参与秒杀的活动
        DateTime startTime = DateUtil.beginOfDay(new Date());
        DateTime endTime = DateUtil.endOfDay(DateUtil.offsetDay(new Date(), 3));
        List<McsSeckillSession> seckillSessions = seckillSessionService.selectByTime(startTime, endTime);
        if (CollectionUtil.isEmpty(seckillSessions)) {
            log.info("秒杀活动列表为空，startTime={}，endTime={}", startTime, endTime);
            return;
        }

        seckillSessions = seckillSessions.stream().map(session -> {
            List<McsSeckillSkuRelation> relations = seckillSkuRelationService.selectBySessionId(session.getId());
            session.setRelations(relations);
            return session;
        }).collect(Collectors.toList());

        // 2、将秒杀信息缓存到redis中
        saveSessionInfos(seckillSessions);
        saveSessionSkuInfos(seckillSessions);
    }

    @Override
    public List<McsSeckillSkuVO> getCurrentSeckillSession() {
        long current = DateUtil.current();

        return null;
    }

    /**
     * 缓存活动信息
     *
     * @param seckillSessions
     */
    private void saveSessionInfos(List<McsSeckillSession> seckillSessions) {
        seckillSessions.stream().forEach(session -> {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SECKILL_SESSION_CACHE_PREFIX + session.getId() + "_" + startTime + "_" + endTime;
            List<String> relations = session.getRelations().stream().map(sku -> sku.getSessionId() + "-" + sku.getSkuId()).collect(Collectors.toList());
            if (!redisUtils.hasKey(key)) {
                redisUtils.lSet(key, relations);
            }
        });
    }

    private void saveSessionSkuInfos(List<McsSeckillSession> seckillSessions) {
        seckillSessions.stream().forEach(session -> {
            List<McsSeckillSkuRelation> relations = session.getRelations();
            if (CollectionUtil.isNotEmpty(relations)) {
                relations.stream().forEach(sku -> {
                    String randomCode = RandomUtil.randomNumbers(8);
                    // 4、保存商品信息到redis中
                    if (!redisUtils.hHasKey(SECKILL_SKU_CACHE_PREFIX, sku.getSessionId() + "-" + sku.getSkuId())) {
                        SeckillSkuRedisTO skuRedisTO = BeanMapperUtils.map(sku, SeckillSkuRedisTO.class);

                        //1、sku基本信息
                        Long skuId = sku.getSkuId();
//                    Result<SkuDTO> skuInfo = productFeignService.getSkuById(skuId);
//                    if (skuInfo != null && skuInfo.getCode().equals(ResultCode.SUCCESS.getCode()) && skuInfo.getData() != null) {
//                        skuRedisTO.setSkuInfo(skuInfo.getData());
//                    } else {
//                        log.error("根据商品ID获取详情详情失败，skuId={}，data={}", skuId, skuInfo);
//                    }

                        skuRedisTO.setStartTime(session.getStartTime().getTime());
                        skuRedisTO.setEndTime(session.getEndTime().getTime());

                        //2、sku秒杀信息

                        //3、随机码
                        skuRedisTO.setRandomCode(randomCode);

                        redisUtils.hset(SECKILL_SKU_CACHE_PREFIX, sku.getSessionId() + "-" + sku.getSkuId(), skuRedisTO);
                        // 5、设置秒杀库存信号量
                        RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_SKU_SEMAPHORE + randomCode);
                        semaphore.trySetPermits(skuRedisTO.getSeckillCount());
                    }
                });
            }
        });
    }
}
