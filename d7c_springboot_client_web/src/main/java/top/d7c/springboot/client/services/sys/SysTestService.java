package top.d7c.springboot.client.services.sys;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.BaseService;
import top.d7c.springboot.common.dos.sys.SysTest;

/**
 * @Title: SysTestService
 * @Package: top.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:42:01
 * @Description: SysTest Service
 */
public interface SysTestService extends BaseService<SysTest, Long> {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年07月04日 12:42:01
     * @Description: 根据条件分页查询列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertTwoDataBase
     * @author: 吴佳隆
     * @data: 2020年7月4日 下午6:22:14
     * @Description: 同时操作两个库数据
     * @return PageResult
     */
    PageResult insertTwoDataBase();

}