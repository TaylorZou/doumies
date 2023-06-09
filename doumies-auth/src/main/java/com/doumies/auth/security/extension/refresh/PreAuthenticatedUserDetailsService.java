package com.doumies.auth.security.extension.refresh;

import com.doumies.auth.security.core.userdetails.member.MemberUserDetailsServiceImpl;
import com.doumies.common.constant.SecurityConstants;
import com.doumies.common.enums.AuthenticationMethodEnum;
import com.doumies.common.web.util.RequestUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 刷新token再次认证 UserDetailsService
 *
 * @author Taylor
 * @date 2023/2/25
 */
@NoArgsConstructor
public class PreAuthenticatedUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    /**
     * 客户端ID和用户服务 UserDetailService 的映射
     *
     * @see com.doumies.auth.security.config.AuthorizationServerConfig#tokenServices(AuthorizationServerEndpointsConfigurer)
     */
    private Map<String, UserDetailsService> userDetailsServiceMap;

    public PreAuthenticatedUserDetailsService(Map<String, UserDetailsService> userDetailsServiceMap) {
        Assert.notNull(userDetailsServiceMap, "userDetailsService cannot be null.");
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsServiceMap, "UserDetailsService must be set");
    }

    /**
     * 重写PreAuthenticatedAuthenticationProvider 的 preAuthenticatedUserDetailsService 属性，可根据客户端和认证方式选择用户服务 UserDetailService 获取用户信息 UserDetail
     *
     * @param authentication
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        String clientId = RequestUtils.getOAuth2ClientId();
        // 获取认证方式，默认是用户名 username
        AuthenticationMethodEnum authenticationMethodEnum = AuthenticationMethodEnum.getByValue(RequestUtils.getAuthenticationMethod());
        UserDetailsService userDetailsService = userDetailsServiceMap.get(clientId);
        if (clientId.equals(SecurityConstants.APP_CLIENT_ID)) {
            // 移动端的用户体系是会员，认证方式是通过手机号 mobile 认证
            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
            switch (authenticationMethodEnum) {
                case MOBILE:
                    return memberUserDetailsService.loadUserByMobile(authentication.getName());
                default:
                    return memberUserDetailsService.loadUserByUsername(authentication.getName());
            }
        } else if (clientId.equals(SecurityConstants.WEAPP_CLIENT_ID)) {
            // 小程序的用户体系是会员，认证方式是通过微信三方标识 openid 认证
            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
            switch (authenticationMethodEnum) {
                case OPENID:
                    return memberUserDetailsService.loadUserByOpenId(authentication.getName());
                default:
                    return memberUserDetailsService.loadUserByUsername(authentication.getName());
            }
        } else if (clientId.equals(SecurityConstants.ADMIN_CLIENT_ID)) {
            // 管理系统的用户体系是系统用户，认证方式通过用户名 username 认证
            switch (authenticationMethodEnum) {
                default:
                    return userDetailsService.loadUserByUsername(authentication.getName());
            }
        } else {
            return userDetailsService.loadUserByUsername(authentication.getName());
        }
    }
}
