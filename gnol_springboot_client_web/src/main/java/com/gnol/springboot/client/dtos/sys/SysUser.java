package com.gnol.springboot.client.dtos.sys;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysUser
 * @Package: com.gnol.springboot.client.dtos.sys
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午4:05:54
 * @Description: 实现了 org.springframework.security.core.userdetails.UserDetails 接口的 gnol 系统_用户对象
 */
public class SysUser extends com.gnol.springboot.common.dos.sys.SysUser implements UserDetails {
    private static final long serialVersionUID = -854374917973551583L;
    /**
     * 实现了 org.springframework.security.core.GrantedAuthority 接口的 gnol 系统_菜单对象
     */
    private List<SysMenu> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<SysMenu> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return getUserAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus() != null && getStatus().equals(StatusEnum.NORMAL.getKey());
    }

}