package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysId;

/**
 * @Title: BaseSysIdDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2020年04月03日 12:15:02
 * @Description: d7c系统_主键操作基础 Dao
 */
@Repository(value = "baseSysIdDao")
public interface BaseSysIdDao extends BaseDao<SysId, String> {

}