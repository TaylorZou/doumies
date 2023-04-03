package com.doumies.pcs.pojo.vo.admin;

import com.doumies.pcs.pojo.entity.PcsSku;
import com.doumies.pcs.pojo.entity.PcsSpuAttributeValue;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 商品详情视图层对象
 *
 * @author Taylor
 */
@Data
@ToString
public class GoodsDetailVO {

    private Long id;

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    private String picUrl;

    private String[] subPicUrls;

    private String description;

    private String detail;


    private List<PcsSpuAttributeValue> attrList;

    private List<PcsSpuAttributeValue> specList;

    private List<PcsSku> skuList;

}
