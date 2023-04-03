package com.doumies.pcs.pojo.dto.app;

import com.doumies.pcs.pojo.entity.PcsSku;
import com.doumies.pcs.pojo.entity.PcsSpuAttributeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFormDTO {

    private SpuInfo spuInfo;

    private List<PcsSpuAttributeValue> attrValueList;


    private List<PcsSku> skuList;


    @Data
    public static class SpuInfo {
        private Long id;
        private String name;
        private Long categoryId;
        private Long brandId;
        private Long originPrice;
        private Long price;
        private Integer sales;
        private String picUrl;
        private List<String> album;
        private String unit;
        private String description;
        private String detail;
        private Integer status;
    }

}
