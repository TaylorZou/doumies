package com.doumies.mcs.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.mcs.pojo.domain.McsSeckillSession;

import java.util.List;

/**
 * @desc 秒杀活动场次业务接口
 * @author Taylor
 * @date 2021/3/14
 */
public interface IMcsSeckillSessionService extends IService<McsSeckillSession> {

    /**
     * 根据起始时间和结束时间查询秒杀活动列表
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return 秒杀活动列表
     */
    List<McsSeckillSession> selectByTime(DateTime startTime, DateTime endTime);
}
