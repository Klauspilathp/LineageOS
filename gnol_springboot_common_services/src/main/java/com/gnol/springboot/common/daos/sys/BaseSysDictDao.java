package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.pojos.sys.SysDict;

/**
 * @Title: BaseSysDictDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:40:37
 * @Description: gnol 系统字典基础 Dao
 */
@Repository(value = "baseSysDictDao")
public interface BaseSysDictDao extends BaseDao<SysDict, Long> {

}