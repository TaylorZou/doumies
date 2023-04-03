package com.doumies.pcs.pojo.dto.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 锁定库存
 * @author Taylor
 * @createTime 2023-02-27 15:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LockStockDTO  {

    private Long skuId;

    private Integer count;

    private String orderToken;

    private Boolean locked;

}
