package com.d7c.springboot.client.services.test1;

import java.util.List;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.context.BaseService;
import com.d7c.springboot.common.dos.test1.Test1Test;

/**
 * @Title: Test1TestService
 * @Package: com.d7c.springboot.client.services.test1
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

    /**
     * @Title: listByTest1
     * @author: 吴佳隆
     * @data: 2020年7月5日 下午1:30:29
     * @Description: 从 test1.test1_test 表查列表
     * @param pd
     * @return List<Test1Test>
     */
    List<Test1Test> listByTest1(PageData pd);

    /**
     * @Title: insert1
     * @author: 吴佳隆
     * @data: 2020年7月5日 下午1:41:49
     * @Description: 插入数据
     * @param test1
     * @return int
     */
    int insert1(Test1Test test1);

}