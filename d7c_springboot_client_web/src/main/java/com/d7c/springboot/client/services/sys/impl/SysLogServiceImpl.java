package com.d7c.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.springboot.client.daos.sys.ExtSysLogDao;
import com.d7c.springboot.client.services.sys.SysLogService;
import com.d7c.springboot.common.daos.sys.BaseSysLogDao;
import com.d7c.springboot.common.dos.sys.SysLog;

/**
 * @Title: SysLogServiceImpl
 * @Package: com.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: d7c 系统日志 Service 实现
 */
@Service(value = "sysLogServiceImpl")
public class SysLogServiceImpl extends AbstractBaseService<BaseSysLogDao, SysLog, Long> implements SysLogService {
    /**
     * d7c 系统日志扩展 Dao
     */
    @Resource(name = "extSysLogDao")
    private ExtSysLogDao sysLogDao;

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(sysLogDao.listPDPage(page)).setPage(page);
    }

}