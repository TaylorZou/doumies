package com.doumies.pcs.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.doumies.common.base.BaseEntity;
import lombok.Data;

/**
 * @author Taylor
 * @date 2023-2-26
 */
@Data
public class PcsSpuAttributeValue extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private Long attributeId;
    private String name;
    private String value;
    private Integer type;
    private String picUrl;

}
