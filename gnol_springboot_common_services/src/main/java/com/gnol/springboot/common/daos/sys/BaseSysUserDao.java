package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.dos.sys.SysUser;

/**
 * @Title: BaseSysUserDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:35
 * @Description: gnol 系统_用户表基础 Dao
 */
@Repository(value = "baseSysUserDao")
public interface BaseSysUserDao extends BaseDao<SysUser, Long> {

}