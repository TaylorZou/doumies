package com.doumies.pcs.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Taylor
 * @since 2023-02-27 15:44
 */
@Data
public class ProductHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String picUrl;
}
