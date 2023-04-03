package com.doumies.mcs.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.mcs.mapper.McsSeckillSessionMapper;
import com.doumies.mcs.pojo.domain.McsSeckillSession;
import com.doumies.mcs.service.IMcsSeckillSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @desc 秒杀活动场次管理业务实现类
 * @author Taylor
 * @date 2021/3/14
 */
@Service
@Slf4j
public class McsSeckillSessionServiceImpl extends ServiceImpl<McsSeckillSessionMapper, McsSeckillSession> implements IMcsSeckillSessionService {
    @Override
    public List<McsSeckillSession> selectByTime(DateTime startTime, DateTime endTime) {
        log.info("根据起始时间和结束时间查询秒杀活动列表, startTime={}, endTime={}", startTime, endTime);
        QueryWrapper<McsSeckillSession> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1).between("start_time", startTime, endTime).orderByAsc("start_time");

        return this.list(queryWrapper);
    }
}
