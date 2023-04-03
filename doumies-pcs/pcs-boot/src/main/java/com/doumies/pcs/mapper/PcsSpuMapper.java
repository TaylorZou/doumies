package com.doumies.pcs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.pcs.pojo.entity.PcsSpu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Taylor
 */
@Mapper
public interface PcsSpuMapper extends BaseMapper<PcsSpu> {
    List<PcsSpu> list(Page<PcsSpu> page, String name, Long categoryId);
}
