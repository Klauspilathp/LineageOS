package top.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.sys.SysUser;

/**
 * @Title: BaseSysUserDao
 * @Package: top.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:35
 * @Description: d7c 系统_用户表基础 Dao
 */
@Repository(value = "baseSysUserDao")
public interface BaseSysUserDao extends BaseDao<SysUser, Long> {

}