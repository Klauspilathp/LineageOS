package com.gnol.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.context.IdService;
import com.gnol.springboot.client.services.sys.AbstractSysLogBatchService;
import com.gnol.springboot.common.dos.sys.SysLog;

/**
 * @Title: SysLogBatchServiceImpl
 * @Package: com.gnol.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年7月3日 下午3:29:53
 * @Description: 系统批量日志服务
 */
@Service(value = "sysLogBatchServiceImpl")
public class SysLogBatchServiceImpl extends AbstractSysLogBatchService {
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;

    public SysLogBatchServiceImpl() {
        super();
    }

    public SysLogBatchServiceImpl(int sysLogSize) {
        super(sysLogSize);
    }

    @Override
    public SysLog createSysLog(Object... objects) {
        return webCreateSysLog((SysLog) objects[0], (String) objects[1]);
    }

    /**
     * @Title: insertSysLog
     * @author: 吴佳隆
     * @data: 2019年7月3日 下午5:27:30
     * @Description: 请求生成日志
     * @param log           日志对象
     * @param methodName    调用方法名称
     * @return SysLog       日志对象
     */
    public SysLog webCreateSysLog(SysLog log, String methodName) {
        if (log != null) {
            log.setLogId(idService.getLong(SysLog.M.TABLE_NAME));
            log.setLogType(getLogType(methodName).getKey());
        }
        return log;
    }

}