package top.d7c.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.AbstractBaseService;
import top.d7c.plugins.core.context.IdService;
import top.d7c.plugins.tools.lang.RandomNumber;
import top.d7c.springboot.client.daos.sys.ExtSysTestDao;
import top.d7c.springboot.client.services.sys.SysTestService;
import top.d7c.springboot.client.services.test1.Test1TestService;
import top.d7c.springboot.common.daos.sys.BaseSysTestDao;
import top.d7c.springboot.common.dos.sys.SysTest;
import top.d7c.springboot.common.dos.test1.Test1Test;

/**
 * @Title: SysTestServiceImpl
 * @Package: top.d7c.springboot.client.services.sys.impl
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