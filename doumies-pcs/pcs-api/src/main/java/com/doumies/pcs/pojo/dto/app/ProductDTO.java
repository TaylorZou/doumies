package com.doumies.pcs.pojo.dto.app;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private Long price;
    private Integer sales;
    private String pic;
}
