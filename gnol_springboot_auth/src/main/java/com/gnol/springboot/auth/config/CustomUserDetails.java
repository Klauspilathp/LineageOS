package com.gnol.springboot.auth.config;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @Title: CustomUserDetails
 * @Package: com.gnol.springboot.auth.config
 * @author: 吴佳隆
 * @date: 2020年7月17日 下午8:03:35
 * @Description: 自定义 org.springframework.security.core.userdetails.UserDetails
 */
public class CustomUserDetails extends User {
    private static final long serialVersionUID = 6743158972653550380L;
    /**
     * 授权对象中的扩展参数
     */
    private Object params;

    public CustomUserDetails(Object params, String username, String password, boolean enabled,
            Set<SimpleGrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.params = params;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

}