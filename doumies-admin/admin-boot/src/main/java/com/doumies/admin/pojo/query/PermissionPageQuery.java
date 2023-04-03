package com.doumies.admin.pojo.query;

import com.doumies.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限分页查询对象
 *
 * @author Taylor
 * @date 2023/2/24 22:22
 */
@Data
@ApiModel
public class PermissionPageQuery extends BasePageQuery {

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("菜单ID")
    private Long menuId;

}
