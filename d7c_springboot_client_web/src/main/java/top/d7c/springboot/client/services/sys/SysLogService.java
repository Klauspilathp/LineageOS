package top.d7c.springboot.client.services.sys;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.BaseService;
import top.d7c.springboot.common.dos.sys.SysLog;

/**
 * @Title: SysLogService
 * @Package: top.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: d7c 系统日志 Service
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