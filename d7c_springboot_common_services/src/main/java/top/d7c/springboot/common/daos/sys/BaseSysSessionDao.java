package top.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.sys.SysSession;

/**
 * @Title: BaseSysSessionDao
 * @Package: top.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:45
 * @Description: d7c 系统_用户详情权限临时表，用户在登录，下线等情况下触发插入、删除、更新操作，然后以此表关联业务表查询指定范围内的基础 Dao
 */
@Repository(value = "baseSysSessionDao")
public interface BaseSysSessionDao extends BaseDao<SysSession, Long> {

}