package com.doumies.ucs.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.doumies.common.base.BaseEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UcsMember extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer gender;

    private String nickName;

    private String mobile;

    private LocalDate birthday;

    private String avatarUrl;

    private String openid;

    private String sessionKey;

    private Integer status;

    private Integer point;

    @TableLogic(delval = "1",value = "0")
    private Integer deleted;

    @TableField(exist = false)
    private List<UcsAddress> addressList;

    private Long balance;

    private String city;

    private String country;

    private String language;

    private String province;

}
