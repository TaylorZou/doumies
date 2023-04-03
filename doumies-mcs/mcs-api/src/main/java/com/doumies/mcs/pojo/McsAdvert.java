package com.doumies.mcs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.doumies.common.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class McsAdvert extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String title;

    private String picUrl;

    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    private Integer status;

    private Integer sort;

    private String url;

    private String remark;

}
