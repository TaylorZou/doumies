package com.doumies.pcs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.pcs.pojo.dto.admin.GoodsFormDTO;
import com.doumies.pcs.pojo.entity.PcsSpu;
import com.doumies.pcs.pojo.vo.admin.GoodsDetailVO;

import java.util.List;


public interface IPcsSpuService extends IService<PcsSpu> {

    IPage<PcsSpu> list(Page<PcsSpu> page, String name, Long categoryId);

    boolean addGoods(GoodsFormDTO goodsFormDTO);

    boolean removeByGoodsIds(List<Long> spuIds);

    boolean updateGoods(GoodsFormDTO goodsFormDTO);

    GoodsDetailVO getGoodsById(Long id);
}
