package com.doumies.ocs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.ocs.pojo.entity.OcsOrderPay;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Mapper
public interface OrderPayMapper extends BaseMapper<OcsOrderPay> {

}
