package com.doumies.ucs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.ucs.pojo.entity.UcsAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UcsAddressMapper extends BaseMapper<UcsAddress> {

    @Select("<script>" +
            " SELECT * from ums_address where member_id =#{userId} " +
            "</script>")
    List<UcsAddress> listByUserId(Long userId);

}
