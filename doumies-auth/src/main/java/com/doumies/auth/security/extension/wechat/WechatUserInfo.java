package com.doumies.auth.security.extension.wechat;

import lombok.Data;

/**
 * 微信用户信息
 *
 * @author Taylor
 * @date 2023/2/25
 */
@Data
public class WechatUserInfo {
    private String avatarUrl;

    private String city;

    private String country;

    private Integer gender;

    private String language;

    private String nickName;

    private String province;

}
