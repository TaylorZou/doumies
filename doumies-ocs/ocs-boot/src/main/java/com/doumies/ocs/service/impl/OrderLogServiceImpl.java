package com.doumies.ocs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.common.web.util.JwtUtils;
import com.doumies.ocs.mapper.OrderLogMapper;
import com.doumies.ocs.pojo.entity.OcsOrderLog;
import com.doumies.ocs.service.IOrderLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OcsOrderLog> implements IOrderLogService {
    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail) {
        log.info("添加订单操作日志，orderId={}，detail={}", orderId, detail);
        OcsOrderLog orderLog = new OcsOrderLog();
        orderLog.setDetail(detail);
        orderLog.setOrderId(orderId);
        orderLog.setOrderStatus(orderStatus);
        orderLog.setUser(user);
        this.save(orderLog);
    }

    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String detail) {
        Long userId = JwtUtils.getUserId();
        addOrderLogs(orderId, orderStatus, userId.toString(), detail);
    }

}
