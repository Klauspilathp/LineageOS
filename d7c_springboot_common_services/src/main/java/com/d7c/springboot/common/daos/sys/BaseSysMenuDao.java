package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysMenu;

/**
 * @Title: BaseSysMenuDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:07
 * @Description: d7c 系统菜单表基础 Dao
 */
@Repository(value = "baseSysMenuDao")
public interface BaseSysMenuDao extends BaseDao<SysMenu, Integer> {

}