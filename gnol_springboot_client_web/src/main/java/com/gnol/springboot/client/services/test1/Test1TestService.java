package com.gnol.springboot.client.services.test1;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.common.dos.test1.Test1Test;

/**
 * @Title: Test1TestService
 * @Package: com.gnol.springboot.client.services.test1
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:52:38
 * @Description: Test1Test Service
 */
public interface Test1TestService extends BaseService<Test1Test, Long> {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年07月04日 12:52:38
     * @Description: 根据条件分页查询列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

}