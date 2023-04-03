package com.doumies.pcs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doumies.pcs.pojo.dto.app.SkuDTO;
import com.doumies.pcs.pojo.entity.PcsSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PcsSkuMapper extends BaseMapper<PcsSku> {

    @Select("<script>" +
            "  select * from pms_sku where spu_id=#{spuId}" +
            "</script>")
    List<PcsSku> listBySpuId(Long spuId);



    SkuDTO getSkuById(Long id);
}
