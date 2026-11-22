package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.pojos.sys.SysOrg;

/**
 * @Title: BaseSysOrgDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: gnol 系统组织机构基础 Dao
 */
@Repository(value = "baseSysOrgDao")
public interface BaseSysOrgDao extends BaseDao<SysOrg, Long> {

}