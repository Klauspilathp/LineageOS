package com.gnol.springboot.client.config;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @Title: CustomUserDetails
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月17日 下午8:03:35
 * @Description: 自定义 org.springframework.security.core.userdetails.UserDetails
 */
public class CustomUserDetails extends User {
    private static final long serialVersionUID = 6743158972653550380L;
    /**
     * 系统用户主键
     */
    private Long userId;

    public CustomUserDetails(Long userId, String username, String password, boolean enabled,
            Set<SimpleGrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}