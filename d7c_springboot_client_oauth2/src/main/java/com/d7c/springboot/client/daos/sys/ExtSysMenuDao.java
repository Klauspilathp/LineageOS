package com.d7c.springboot.client.daos.sys;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

/**
 * @Title: ExtSysMenuDao
 * @Package: com.d7c.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: d7c 系统菜单表扩展 Dao
 */
@Repository(value = "extSysMenuDao")
public interface ExtSysMenuDao {

    /**
     * @Title: listPermissionsByRoleId
     * @author: 吴佳隆
     * @data: 2019年7月1日 下午4:01:08
     * @Description: 根据角色编号查询权限列表
     * @param roleId
     * @return Set<SimpleGrantedAuthority>
     */
    Set<SimpleGrantedAuthority> listPermissionsByRoleId(Long roleId);

}