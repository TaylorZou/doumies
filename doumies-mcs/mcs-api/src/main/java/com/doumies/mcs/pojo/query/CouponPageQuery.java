package com.doumies.mcs.pojo.query;

import com.doumies.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @desc: 优惠券领取使用详情条件分页查询
 * @author Taylor
 * @date 2021/3/10
 */
@ApiModel("优惠券领取使用详情条件分页查询")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponPageQuery extends BasePageQuery {

    @ApiModelProperty("优惠券模板ID(必传)")
    private String templateId;

    @ApiModelProperty("状态")
    private Integer state;

    @ApiModelProperty("优惠券码")
    private String couponCode;
}
