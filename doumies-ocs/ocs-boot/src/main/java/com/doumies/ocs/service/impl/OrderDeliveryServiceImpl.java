package com.doumies.ocs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.ocs.mapper.OrderDeliveryMapper;
import com.doumies.ocs.pojo.entity.OcsOrderDelivery;
import com.doumies.ocs.service.IOrderDeliveryService;
import org.springframework.stereotype.Service;

@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl extends ServiceImpl<OrderDeliveryMapper, OcsOrderDelivery> implements IOrderDeliveryService {

}
