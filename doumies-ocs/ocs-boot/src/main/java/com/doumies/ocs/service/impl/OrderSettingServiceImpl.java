package com.doumies.ocs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.ocs.mapper.OrderSettingMapper;
import com.doumies.ocs.pojo.entity.OcsOrderSetting;
import com.doumies.ocs.service.IOrderSettingService;
import org.springframework.stereotype.Service;


@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OcsOrderSetting> implements IOrderSettingService {

}
