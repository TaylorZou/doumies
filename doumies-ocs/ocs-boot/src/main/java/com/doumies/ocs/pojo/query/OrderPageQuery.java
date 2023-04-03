package com.doumies.ocs.pojo.query;

import com.doumies.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author Taylor
 * @date 2021-12-13
 */
@Data
@ApiModel("订单分页查询对象")
public class OrderPageQuery extends BasePageQuery {

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty(value = "开始时间(格式：yyyy-MM-dd)")
    private String beginDate;

    @ApiModelProperty(value = "截止时间(格式：yyyy-MM-dd)")
    private String endDate;

}
