package com.doumies.mcs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.mcs.pojo.domain.McsCouponRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface McsCouponRecordMapper extends BaseMapper<McsCouponRecord> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(McsCouponRecord record);

    McsCouponRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(McsCouponRecord record);

    int updateByPrimaryKey(McsCouponRecord record);
}