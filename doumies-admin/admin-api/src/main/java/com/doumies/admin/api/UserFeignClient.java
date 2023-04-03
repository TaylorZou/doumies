package com.doumies.admin.api;

import com.doumies.admin.api.fallback.UserFeignFallbackClient;
import com.doumies.admin.dto.AuthUserDTO;
import com.doumies.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "doumies-admin", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/api/v1/users/username/{username}")
    Result<AuthUserDTO> getUserByUsername(@PathVariable String username);
}
