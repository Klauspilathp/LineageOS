package top.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.sys.SysTest;

/**
 * @Title: BaseSysTestDao
 * @Package: top.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:42:01
 * @Description: SysTest基础 Dao
 */
@Repository(value = "baseSysTestDao")
public interface BaseSysTestDao extends BaseDao<SysTest, Long> {

}