package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.dos.sys.SysTerritory;

/**
 * @Title: BaseSysTerritoryDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:30
 * @Description: gnol 系统地域基础 Dao
 */
@Repository(value = "baseSysTerritoryDao")
public interface BaseSysTerritoryDao extends BaseDao<SysTerritory, Long> {

}