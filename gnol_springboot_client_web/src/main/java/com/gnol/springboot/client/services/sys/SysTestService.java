package com.gnol.springboot.client.services.sys;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.common.pojos.sys.SysTest;

/**
 * @Title: SysTestService
 * @Package: com.gnol.springboot.client.services.sys
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

}