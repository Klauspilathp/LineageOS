package com.gnol.springboot.client.services.sys;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.common.pojos.sys.SysScheduleTrigger;

/**
 * @Title: SysScheduleTriggerService
 * @Package: com.gnol.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2020年05月09日 14:02:58
 * @Description: gnol系统_任务调度 Service
 */
public interface SysScheduleTriggerService extends BaseService<SysScheduleTrigger, Long> {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 根据条件分页查询gnol系统_任务调度列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertScheduleTrigger
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 新增gnol系统_任务调度
     * @param pd
     * @return PageResult
     */
    PageResult insertScheduleTrigger(PageData pd);

    /**
     * @Title: updateScheduleTrigger
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 修改gnol系统_任务调度
     * @param pd
     * @return PageResult
     */
    PageResult updateScheduleTrigger(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 软删除gnol系统_任务调度
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

}