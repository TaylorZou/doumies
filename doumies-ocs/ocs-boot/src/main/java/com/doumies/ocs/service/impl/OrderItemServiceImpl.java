package com.doumies.ocs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.ocs.mapper.OrderItemMapper;
import com.doumies.ocs.pojo.entity.OcsOrderItem;
import com.doumies.ocs.service.IOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OcsOrderItem> implements IOrderItemService {


}
