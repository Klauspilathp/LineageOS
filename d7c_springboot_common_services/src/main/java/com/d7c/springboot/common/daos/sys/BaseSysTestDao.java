package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysTest;

/**
 * @Title: BaseSysTestDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:42:01
 * @Description: SysTest基础 Dao
 */
@Repository(value = "baseSysTestDao")
public interface BaseSysTestDao extends BaseDao<SysTest, Long> {

}