package com.doumies.ocs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.ocs.pojo.entity.OcsOrderSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Mapper
public interface OrderSettingMapper extends BaseMapper<OcsOrderSetting> {

}
