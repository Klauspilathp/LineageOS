package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.pojos.sys.SysRoleMenu;

/**
 * @Title: BaseSysRoleMenuDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:49
 * @Description: gnol 系统角色菜单表基础 Dao
 */
@Repository(value = "baseSysRoleMenuDao")
public interface BaseSysRoleMenuDao extends BaseDao<SysRoleMenu, Long> {

}