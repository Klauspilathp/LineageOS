package com.gnol.springboot.client.dtos.sys;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Title: SysMenu
 * @Package: com.gnol.springboot.client.dtos.sys
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午4:17:43
 * @Description: 实现了 org.springframework.security.core.GrantedAuthority 接口的 gnol 系统_菜单对象
 */
public class SysMenu extends com.gnol.springboot.common.dos.sys.SysMenu implements GrantedAuthority {
    private static final long serialVersionUID = -6731970081644270166L;

    @Override
    public String getAuthority() {
        return getPermissions();
    }

}