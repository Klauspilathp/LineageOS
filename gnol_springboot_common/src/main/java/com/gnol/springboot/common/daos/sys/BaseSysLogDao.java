package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.pojos.sys.SysLog;

/**
 * @Title: BaseSysLogDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统日志基础 Dao
 */
@Repository(value = "baseSysLogDao")
public interface BaseSysLogDao extends BaseDao<SysLog, Long> {

}