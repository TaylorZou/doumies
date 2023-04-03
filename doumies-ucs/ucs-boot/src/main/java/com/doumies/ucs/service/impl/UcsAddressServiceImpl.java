package com.doumies.ucs.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.common.constant.GlobalConstants;
import com.doumies.common.web.util.JwtUtils;
import com.doumies.ucs.mapper.UcsAddressMapper;
import com.doumies.ucs.pojo.entity.UcsAddress;
import com.doumies.ucs.service.IUcsAddressService;
import org.springframework.stereotype.Service;

@Service
public class UcsAddressServiceImpl extends ServiceImpl<UcsAddressMapper, UcsAddress> implements IUcsAddressService {


    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    @Override
    public boolean addAddress(UcsAddress address) {
        Long memberId = JwtUtils.getUserId();
        address.setMemberId(memberId);
        if (GlobalConstants.STATUS_YES.equals(address.getDefaulted())) { // 修改其他默认地址为非默认
            this.update(new LambdaUpdateWrapper<UcsAddress>()
                    .eq(UcsAddress::getMemberId, memberId)
                    .eq(UcsAddress::getDefaulted, 1)
                    .set(UcsAddress::getDefaulted, 0)
            );
        }
        return this.save(address);
    }


    /**
     * 修改地址
     *
     * @param address
     * @return
     */
    @Override
    public boolean updateAddress(UcsAddress address) {
        Long loginUserId = JwtUtils.getUserId();
        // 修改其他默认地址为非默认
        if (GlobalConstants.STATUS_YES.equals(address.getDefaulted())) {
            this.update(new LambdaUpdateWrapper<UcsAddress>()
                    .eq(UcsAddress::getMemberId, loginUserId)
                    .eq(UcsAddress::getDefaulted, 1)
                    .set(UcsAddress::getDefaulted, 0)
            );
        }
        return this.updateById(address);
    }

}
