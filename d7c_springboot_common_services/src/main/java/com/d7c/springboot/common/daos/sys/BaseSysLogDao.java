package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysLog;

/**
 * @Title: BaseSysLogDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: d7c 系统日志基础 Dao
 */
@Repository(value = "baseSysLogDao")
public interface BaseSysLogDao extends BaseDao<SysLog, Long> {

}