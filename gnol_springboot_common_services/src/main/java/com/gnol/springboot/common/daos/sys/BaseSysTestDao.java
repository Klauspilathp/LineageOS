package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.springboot.common.pojos.sys.SysTest;
import com.gnol.plugins.core.context.BaseDao;

/**
 * @Title: BaseSysTestDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:42:01
 * @Description: SysTest基础 Dao
 */
@Repository(value = "baseSysTestDao")
public interface BaseSysTestDao extends BaseDao<SysTest, Long> {

}