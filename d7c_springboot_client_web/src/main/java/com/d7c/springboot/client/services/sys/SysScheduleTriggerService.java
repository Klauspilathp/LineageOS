package com.d7c.springboot.client.services.sys;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.context.BaseService;
import com.d7c.springboot.common.dos.sys.SysScheduleTrigger;

/**
 * @Title: SysScheduleTriggerService
 * @Package: com.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2020年05月09日 14:02:58
 * @Description: d7c系统_任务调度 Service
 */
public interface SysScheduleTriggerService extends BaseService<SysScheduleTrigger, Long> {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 根据条件分页查询d7c系统_任务调度列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertScheduleTrigger
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 新增d7c系统_任务调度
     * @param pd
     * @return PageResult
     */
    PageResult insertScheduleTrigger(PageData pd);

    /**
     * @Title: updateScheduleTrigger
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 修改d7c系统_任务调度
     * @param pd
     * @return PageResult
     */
    PageResult updateScheduleTrigger(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年05月09日 14:02:58
     * @Description: 软删除d7c系统_任务调度
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

}