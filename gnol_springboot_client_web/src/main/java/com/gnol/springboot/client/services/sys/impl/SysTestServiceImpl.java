package com.gnol.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.plugins.core.context.IdService;
import com.gnol.plugins.tools.lang.RandomNumber;
import com.gnol.springboot.client.daos.sys.ExtSysTestDao;
import com.gnol.springboot.client.services.sys.SysTestService;
import com.gnol.springboot.client.services.test1.Test1TestService;
import com.gnol.springboot.common.daos.sys.BaseSysTestDao;
import com.gnol.springboot.common.dos.sys.SysTest;
import com.gnol.springboot.common.dos.test1.Test1Test;

/**
 * @Title: SysTestServiceImpl
 * @Package: com.gnol.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:42:01
 * @Description: SysTest Service 实现
 */
@Service(value = "sysTestServiceImpl")
public class SysTestServiceImpl extends AbstractBaseService<BaseSysTestDao, SysTest, Long> implements SysTestService {
    /**
     * SysTest扩展 Dao
     */
    @Resource(name = "extSysTestDao")
    private ExtSysTestDao sysTestDao;
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;
    /**
     * Test1Test Service 实现
     */
    @Resource(name = "test1TestServiceImpl")
    private Test1TestService test1TestService;

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(sysTestDao.listPDPage(page)).setPage(page);
    }

    @Override
    public PageResult insertTwoDataBase() {
        int random = RandomNumber.getUnboundedInt();
        SysTest test = new SysTest();
        test.setName("insert two database by sys_test " + random);
        int insert = dao.insert(test);

        // int a = 1 / 0;

        Test1Test test1 = new Test1Test();
        test1.setText("insert two database by test1_test " + random);
        int insert1 = test1TestService.insert1(test1);
        return PageResult.ok("sys_test：" + insert + ", test1_test：" + insert1 + ", random：" + random + "。");
    }

}