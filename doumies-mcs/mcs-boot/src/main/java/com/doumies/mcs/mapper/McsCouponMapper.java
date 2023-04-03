package com.doumies.mcs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.mcs.pojo.domain.McsCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface McsCouponMapper extends BaseMapper<McsCoupon> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(McsCoupon record);

    McsCoupon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(McsCoupon record);

    int updateByPrimaryKey(McsCoupon record);

    int updateTakeStock(@Param("id") String couponId);
}