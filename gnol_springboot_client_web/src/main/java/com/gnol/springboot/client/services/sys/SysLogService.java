package com.gnol.springboot.client.services.sys;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.common.pojos.sys.SysLog;

/**
 * @Title: SysLogService
 * @Package: com.gnol.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统日志 Service
 */
public interface SysLogService extends BaseService<SysLog, Long> {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询日志列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

}