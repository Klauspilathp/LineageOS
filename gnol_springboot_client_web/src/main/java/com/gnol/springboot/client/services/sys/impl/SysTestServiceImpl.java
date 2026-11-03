package com.gnol.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.plugins.core.context.IdService;
import com.gnol.springboot.client.daos.sys.ExtSysTestDao;
import com.gnol.springboot.client.services.sys.SysTestService;
import com.gnol.springboot.common.daos.sys.BaseSysTestDao;
import com.gnol.springboot.common.pojos.sys.SysTest;

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

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(sysTestDao.listPDPage(page)).setPage(page);
    }

}