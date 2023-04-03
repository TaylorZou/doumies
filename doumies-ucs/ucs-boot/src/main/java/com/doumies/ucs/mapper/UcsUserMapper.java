package com.doumies.ucs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.ucs.pojo.entity.UcsMember;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UcsUserMapper extends BaseMapper<UcsMember> {

    @Select("<script>" +
            " SELECT * from ums_member " +
            " <if test ='nickname !=null and nickname.trim() neq \"\" ' >" +
            "       WHERE nick_name like concat('%',#{nickname},'%')" +
            " </if>" +
            " ORDER BY gmt_modified DESC, gmt_create DESC" +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "addressList", column = "id", many = @Many(select = "com.doumies.ucs.mapper.UcsAddressMapper.listByUserId"))
    })
    List<UcsMember> list(Page<UcsMember> page, String nickname);


}
