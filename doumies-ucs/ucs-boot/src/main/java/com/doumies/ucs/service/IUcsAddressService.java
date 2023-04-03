package com.doumies.ucs.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.ucs.pojo.entity.UcsAddress;

public interface IUcsAddressService extends IService<UcsAddress> {

    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    boolean addAddress(UcsAddress address);

    /**
     * 修改地址
     *
     * @param address
     * @return
     */
    boolean updateAddress(UcsAddress address);


}
