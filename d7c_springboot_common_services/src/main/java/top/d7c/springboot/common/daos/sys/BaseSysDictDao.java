package top.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.sys.SysDict;

/**
 * @Title: BaseSysDictDao
 * @Package: top.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:40:37
 * @Description: d7c 系统字典基础 Dao
 */
@Repository(value = "baseSysDictDao")
public interface BaseSysDictDao extends BaseDao<SysDict, Long> {

}