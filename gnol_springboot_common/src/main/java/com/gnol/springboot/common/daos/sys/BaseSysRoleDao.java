package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.pojos.sys.SysRole;

/**
 * @Title: BaseSysRoleDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:57
 * @Description: gnol 系统角色表基础 Dao
 */
@Repository(value = "baseSysRoleDao")
public interface BaseSysRoleDao extends BaseDao<SysRole, Long> {

}