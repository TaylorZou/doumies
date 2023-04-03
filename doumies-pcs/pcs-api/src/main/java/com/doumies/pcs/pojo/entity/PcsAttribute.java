package com.doumies.pcs.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.doumies.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PcsAttribute extends BaseEntity {

    @TableId(type=IdType.AUTO)
    private Long id;

    private Long categoryId;

    private String name;

    /**
     * 类型(1:规格;2:属性;)
     */
    private Integer type;
}
