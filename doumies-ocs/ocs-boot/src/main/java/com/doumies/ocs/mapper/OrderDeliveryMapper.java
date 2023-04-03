package com.doumies.ocs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.ocs.pojo.entity.OcsOrderDelivery;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单物流记录表
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Mapper
public interface OrderDeliveryMapper extends BaseMapper<OcsOrderDelivery> {

}
