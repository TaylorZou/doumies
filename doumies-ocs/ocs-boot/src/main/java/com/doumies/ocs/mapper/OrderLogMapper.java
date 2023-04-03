package com.doumies.ocs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.ocs.pojo.entity.OcsOrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Mapper
public interface OrderLogMapper extends BaseMapper<OcsOrderLog> {

}
