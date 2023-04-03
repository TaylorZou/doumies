package com.doumies.pcs.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.doumies.common.base.BaseEntity;
import lombok.Data;

@Data
public class PcsSku extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sn;
    private String name;
    private Long spuId;
    private String specIds;
    private Long price;
    private Integer stock;
    private Integer lockedStock;
    private String picUrl;
}
