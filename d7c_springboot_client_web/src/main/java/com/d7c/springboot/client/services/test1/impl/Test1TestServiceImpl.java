package com.d7c.springboot.client.services.test1.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.mybatis.springboot.autoconfigure.CurrDataSource;
import com.d7c.mybatis.springboot.autoconfigure.DataSourceType;
import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.plugins.core.context.IdService;
import com.d7c.springboot.client.daos.test1.ExtTest1TestDao;
import com.d7c.springboot.client.services.test1.Test1TestService;
import com.d7c.springboot.common.daos.test1.BaseTest1TestDao;
import com.d7c.springboot.common.dos.test1.Test1Test;

/**
 * @Title: Test1TestServiceImpl
 * @Package: com.d7c.springboot.client.services.test1.impl
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:52:38
 * @Description: Test1Test Service 实现
 */
@Service(value = "test1TestServiceImpl")
public class Test1TestServiceImpl extends AbstractBaseService<BaseTest1TestDao, Test1Test, Long>
        implements Test1TestService {
    /**
     * Test1Test扩展 Dao
     */
    @Resource(name = "extTest1TestDao")
    private ExtTest1TestDao test1TestDao;
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(test1TestDao.listPDPage(page)).setPage(page);
    }

    @CurrDataSource(DataSourceType.SLAVE)
    @Override
    public List<Test1Test> listByTest1(PageData pd) {
        return dao.listBy(pd);
    }

    @CurrDataSource(DataSourceType.SLAVE)
    @Override
    public int insert1(Test1Test test1) {
        return dao.insert(test1);
    }

}