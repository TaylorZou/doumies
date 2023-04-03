package com.doumies.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doumies.admin.pojo.entity.SysOauthClient;

public interface ISysOauthClientService extends IService<SysOauthClient> {
    void cleanCache();
}
