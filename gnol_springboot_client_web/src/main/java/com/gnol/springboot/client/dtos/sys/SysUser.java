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

    // 获取当前用户对应的角色列表
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

    // 账户是否未过期，过期无法验证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 指定用户是否解锁，锁定的用户无法进行身份验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 指示用户凭据是否已过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 用户是否已被禁用，禁用的用户不进行身份验证
    @Override
    public boolean isEnabled() {
        return getStatus() == null || !getStatus().equals(StatusEnum.NORMAL.getKey());
    }

}