package com.gnol.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.springboot.client.daos.sys.ExtSysLogDao;
import com.gnol.springboot.client.services.sys.SysLogService;
import com.gnol.springboot.common.daos.sys.BaseSysLogDao;
import com.gnol.springboot.common.dos.sys.SysLog;

/**
 * @Title: SysLogServiceImpl
 * @Package: com.gnol.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: gnol 系统日志 Service 实现
 */
@Service(value = "sysLogServiceImpl")
public class SysLogServiceImpl extends AbstractBaseService<BaseSysLogDao, SysLog, Long> implements SysLogService {
    /**
     * gnol 系统日志扩展 Dao
     */
    @Resource(name = "extSysLogDao")
    private ExtSysLogDao sysLogDao;

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(sysLogDao.listPDPage(page)).setPage(page);
    }

}