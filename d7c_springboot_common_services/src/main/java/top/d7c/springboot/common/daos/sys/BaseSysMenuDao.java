package top.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.sys.SysMenu;

/**
 * @Title: BaseSysMenuDao
 * @Package: top.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:07
 * @Description: d7c 系统菜单表基础 Dao
 */
@Repository(value = "baseSysMenuDao")
public interface BaseSysMenuDao extends BaseDao<SysMenu, Integer> {

}