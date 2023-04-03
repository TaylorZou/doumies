package com.doumies.ucs.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.pcs.pojo.vo.ProductHistoryVO;
import com.doumies.ucs.pojo.entity.UcsMember;

import java.util.Set;

public interface IUcsMemberService extends IService<UcsMember> {

    IPage<UcsMember> list(Page<UcsMember> page, String nickname);

    void addProductViewHistory(ProductHistoryVO product, Long userId);

    Set<ProductHistoryVO> getProductViewHistory(Long userId);
}
