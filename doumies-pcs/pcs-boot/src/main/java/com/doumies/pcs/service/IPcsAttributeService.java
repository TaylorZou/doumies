package com.doumies.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.pcs.pojo.dto.admin.AttributeFormDTO;
import com.doumies.pcs.pojo.entity.PcsAttribute;

public interface IPcsAttributeService extends IService<PcsAttribute> {

    boolean saveBatch(AttributeFormDTO attributeForm);
}
