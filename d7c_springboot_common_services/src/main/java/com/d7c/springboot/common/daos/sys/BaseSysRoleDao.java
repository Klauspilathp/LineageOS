package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysRole;

/**
 * @Title: BaseSysRoleDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:57
 * @Description: d7c 系统角色表基础 Dao
 */
@Repository(value = "baseSysRoleDao")
public interface BaseSysRoleDao extends BaseDao<SysRole, Long> {

}