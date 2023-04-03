package com.doumies.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.common.result.Result;
import com.doumies.pcs.pojo.dto.app.LockStockDTO;
import com.doumies.pcs.pojo.dto.app.SkuDTO;
import com.doumies.pcs.pojo.entity.PcsSku;

import java.util.List;

public interface IPcsSkuService extends IService<PcsSku> {

    /**
     * 锁定库存
     */
    Result lockStock(List<LockStockDTO> list);

    /**
     * 锁定库存
     */
   // Boolean lockStockTcc(List<LockStockDTO> list);

    /**
     * 解锁库存
     */
    boolean unlockStock(String orderToken);

    /**
     * 扣减库存
     */
    boolean deductStock(String orderToken);

    /**
     * 获取商品库存数量
     */
    Integer getStockById(Long id);

    SkuDTO getSkuById(Long id);
}
