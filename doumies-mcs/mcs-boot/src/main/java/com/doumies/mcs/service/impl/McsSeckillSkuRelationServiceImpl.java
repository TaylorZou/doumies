package com.doumies.mcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.mcs.mapper.McsSeckillSkuRelationMapper;
import com.doumies.mcs.pojo.domain.McsSeckillSkuRelation;
import com.doumies.mcs.service.IMcsSeckillSkuRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @desc 秒杀活动场次商品关联业务实现类
 * @author Taylor
 * @date 2021/3/14
 */
@Service
@Slf4j
public class McsSeckillSkuRelationServiceImpl extends ServiceImpl<McsSeckillSkuRelationMapper, McsSeckillSkuRelation> implements IMcsSeckillSkuRelationService {
    @Override
    public List<McsSeckillSkuRelation> selectBySessionId(Long sessionId) {
        log.info("根据秒杀活动场次ID查询关联商品列表，sessionId={}",sessionId);
        QueryWrapper<McsSeckillSkuRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("session_id",sessionId);
        return this.list(queryWrapper);
    }
}
