package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysOrg;

/**
 * @Title: BaseSysOrgDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: d7c 系统组织机构基础 Dao
 */
@Repository(value = "baseSysOrgDao")
public interface BaseSysOrgDao extends BaseDao<SysOrg, Long> {

}