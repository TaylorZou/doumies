package com.doumies.pcs.pojo.dto.admin;

import com.doumies.pcs.pojo.entity.PcsSku;
import lombok.Data;

import java.util.List;

/**
 * @author Taylor
 */
@Data
public class GoodsFormDTO {

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

    private List<AttributeValue> attrList;

    private List<AttributeValue> specList;

    private List<PcsSku> skuList;

    @Data
    public static class AttributeValue{

        private Long attributeId;

        private String id;

        private String name;

        private String value;

        private String picUrl;

    }
}
