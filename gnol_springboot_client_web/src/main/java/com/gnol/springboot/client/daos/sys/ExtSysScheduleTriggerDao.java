package com.gnol.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;

/**
 * @Title: ExtSysScheduleTriggerDao
 * @Package: com.gnol.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2020年05月09日 14:36:23
 * @Description: gnol系统_任务调度扩展 Dao
 */
@Repository(value = "extSysScheduleTriggerDao")
public interface ExtSysScheduleTriggerDao {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:36:23
     * @Description: 根据条件分页查询gnol系统_任务调度列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:36:23
     * @Description: 根据gnol系统_任务调度编号软删除gnol系统_任务调度
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

}