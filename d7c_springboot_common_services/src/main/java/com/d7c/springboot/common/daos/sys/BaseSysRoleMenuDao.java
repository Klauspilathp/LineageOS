package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysRoleMenu;

/**
 * @Title: BaseSysRoleMenuDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:49
 * @Description: d7c 系统角色菜单表基础 Dao
 */
@Repository(value = "baseSysRoleMenuDao")
public interface BaseSysRoleMenuDao extends BaseDao<SysRoleMenu, Long> {

}