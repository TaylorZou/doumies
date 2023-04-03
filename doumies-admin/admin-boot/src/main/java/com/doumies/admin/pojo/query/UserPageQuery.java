package com.doumies.admin.pojo.query;

import com.doumies.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页查询对象
 *
 * @author Taylor
 * @date 2023/2/24 22:22
 */
@Data
@ApiModel
public class UserPageQuery extends BasePageQuery {

    @ApiModelProperty("关键字(用户名、昵称、手机号)")
    private String keywords;

    @ApiModelProperty("用户状态")
    private Integer status;

    @ApiModelProperty("部门ID")
    private Long deptId;

}
