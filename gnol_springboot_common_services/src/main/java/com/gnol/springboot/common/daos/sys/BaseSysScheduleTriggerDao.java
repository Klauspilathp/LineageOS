package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.dos.sys.SysScheduleTrigger;

/**
 * @Title: BaseSysScheduleTriggerDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2020年05月09日 14:02:58
 * @Description: gnol系统_任务调度基础 Dao
 */
@Repository(value = "baseSysScheduleTriggerDao")
public interface BaseSysScheduleTriggerDao extends BaseDao<SysScheduleTrigger, Long> {

}