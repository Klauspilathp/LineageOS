package top.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.sys.SysTerritory;

/**
 * @Title: BaseSysTerritoryDao
 * @Package: top.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:30
 * @Description: d7c 系统地域基础 Dao
 */
@Repository(value = "baseSysTerritoryDao")
public interface BaseSysTerritoryDao extends BaseDao<SysTerritory, Long> {

}