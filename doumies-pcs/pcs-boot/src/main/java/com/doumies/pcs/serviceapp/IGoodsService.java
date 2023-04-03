package com.doumies.pcs.serviceapp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.pcs.pojo.entity.PcsSpu;
import com.doumies.pcs.pojo.vo.app.GoodsDetailVO;

/**
 * @author Taylor
 * @date 2023/2/28
 */
public interface IGoodsService extends IService<PcsSpu> {
    GoodsDetailVO getGoodsById(Long id);

    GoodsDetailVO getGoodsBySkuId(Long skuId);
}
