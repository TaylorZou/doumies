package com.doumies.pcs.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.doumies.common.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class PcsBrand extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private String logoUrl;

    private Integer sort;
}
