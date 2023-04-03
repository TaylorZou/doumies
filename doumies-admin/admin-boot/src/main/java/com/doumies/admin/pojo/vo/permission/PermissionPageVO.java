package com.doumies.admin.pojo.vo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限视图对象
 *
 * @author Taylor
 * @date 2023-2-25
 */
@Data
@ApiModel("权限视图对象")
public class PermissionPageVO {

    @ApiModelProperty("权限ID")
    private Long id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("URL权限标识-服务名称")
    private String serviceName;

    @ApiModelProperty("URL权限标识-请求标识")
    private String requestMethod;

    @ApiModelProperty("URL权限标识-请求方式")
    private String requestPath;

    @ApiModelProperty("按钮权限标识")
    private String btnPerm;

}
