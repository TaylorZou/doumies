package com.doumies.ocs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.ocs.pojo.entity.OcsOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单商品明细表
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OcsOrderItem> {

}
