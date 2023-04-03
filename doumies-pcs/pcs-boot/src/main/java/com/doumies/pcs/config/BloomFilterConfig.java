package com.doumies.pcs.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.doumies.pcs.common.constant.PcsConstants;
import com.doumies.pcs.component.BloomRedisService;
import com.doumies.pcs.pojo.entity.PcsSpu;
import com.doumies.pcs.serviceapp.IGoodsService;
import com.doumies.pcs.utils.BloomFilterUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author Taylor
 * @Description
 * @Date 2023/2/26 9:39
 **/
@Slf4j
@Configuration
@AllArgsConstructor
public class BloomFilterConfig implements InitializingBean {

    private final IGoodsService goodsService;
    private final RedisTemplate redisTemplate;

    @Bean
    public BloomFilterUtils<String> initBloomFilterHelper() {
        return new BloomFilterUtils<>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8)
                .putString(from, Charsets.UTF_8), 1000000, 0.01);
    }


    @Bean
    public BloomRedisService bloomRedisService() {
        BloomRedisService bloomRedisService = new BloomRedisService();
        bloomRedisService.setBloomFilterUtils(initBloomFilterHelper());
        bloomRedisService.setRedisTemplate(redisTemplate);
        return bloomRedisService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<PcsSpu> list = goodsService.list();
        log.info("加载产品到布隆过滤器当中,size:{}", list.size());
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().filter(item -> item.getId() > 0).forEach(item -> {
                bloomRedisService().addByBloomFilter(PcsConstants.GOODS_BLOOM_FILTER, item.getId() + "");
            });
        }
    }
}
