package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.dos.sys.SysMenu;

/**
 * @Title: BaseSysMenuDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:07
 * @Description: gnol 系统菜单表基础 Dao
 */
@Repository(value = "baseSysMenuDao")
public interface BaseSysMenuDao extends BaseDao<SysMenu, Integer> {

}