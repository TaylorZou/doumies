package com.doumies.ocs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.ocs.pojo.entity.OcsOrderLog;

/**
 * 订单操作历史记录
 *
 * @author Taylor
 * @date 2021-12-13
 */
public interface IOrderLogService extends IService<OcsOrderLog> {

    /**
     * 添加订单操作日志记录
     * @param orderId 订单ID
     * @param orderStatus 订单状态
     * @param user 操作人员
     * @param detail 描述信息
     */
    void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail);

    /**
     * 添加订单操作日志记录
     * @param orderId 订单ID
     * @param orderStatus 订单状态
     * @param detail 描述
     */
    void addOrderLogs(Long orderId, Integer orderStatus, String detail);
}

