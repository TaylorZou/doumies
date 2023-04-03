package com.doumies.admin.api;

import com.doumies.admin.dto.AuthClientDTO;
import com.doumies.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "doumies-admin", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @GetMapping("/api/v1/oauth-clients/getOAuth2ClientById")
    Result<AuthClientDTO> getOAuth2ClientById(@RequestParam String clientId);
}
