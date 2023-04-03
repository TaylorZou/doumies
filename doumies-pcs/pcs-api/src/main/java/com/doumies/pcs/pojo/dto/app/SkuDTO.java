package com.doumies.pcs.pojo.dto.app;

import lombok.Data;

/**
 * @author Taylor
 * @desc
 * @date 2023/2/27
 */
@Data
public class SkuDTO {

    private Long id;
    private String sn;
    private String name;
    private String picUrl;
    private Long price;

    private Integer stock;
    private String goodsName;

}
