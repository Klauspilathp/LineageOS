package com.d7c.springboot.client.services.flowable.impl;

import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.security.FlowableAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Title: FlowableUserDetailsServiceImpl
 * @Package: com.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 上午11:46:42
 * @Description: flowable 用户认证服务。
 */
@Service
public class FlowableUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private FlowableSecurityUserImpl flowableSecurityUserImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RemoteUser user = flowableSecurityUserImpl.getRemoteUser();
        return new FlowableAppUser(user, user.getId(), flowableSecurityUserImpl.getGrantedAuthoritys());
    }

}
