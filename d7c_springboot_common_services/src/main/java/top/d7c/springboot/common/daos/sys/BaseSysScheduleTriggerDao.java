package top.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.sys.SysScheduleTrigger;

/**
 * @Title: BaseSysScheduleTriggerDao
 * @Package: top.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2020年05月09日 14:02:58
 * @Description: d7c系统_任务调度基础 Dao
 */
@Repository(value = "baseSysScheduleTriggerDao")
public interface BaseSysScheduleTriggerDao extends BaseDao<SysScheduleTrigger, Long> {

}