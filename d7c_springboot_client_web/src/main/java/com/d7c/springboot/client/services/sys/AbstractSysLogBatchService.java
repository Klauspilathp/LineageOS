package com.d7c.springboot.client.services.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.common.daos.sys.BaseSysLogDao;
import com.d7c.springboot.common.dos.sys.SysLog;
import com.d7c.springboot.common.enums.sys.LogTypeEnum;

/**
 * @Title: AbstractSysLogBatchService
 * @Package: com.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年7月3日 下午3:29:53
 * @Description: 系统批量日志服务
 */
public abstract class AbstractSysLogBatchService implements SysLogBatchService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractSysLogBatchService.class);
    /**
     * spring 线程池对象
     */
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    /**
     * d7c 系统日志基础 Dao
     */
    @Resource(name = "baseSysLogDao")
    private BaseSysLogDao sysLogDao;
    /**
     * 设置每次插入日志数据
     */
    private int sysLogSize;
    /**
     * 日志存放容器
     * Collections.synchronizedList(new ArrayList<String>())：写操作使用了同步锁。写快读慢
     * new CopyOnWriteArrayList<String>()：读操作的速度更快，并发性更好。写慢读快
     */
    private final List<SysLog> sysLogList0;
    private final List<SysLog> sysLogList1;
    /**
     * Long 的原子操作类，实现类似自增
     */
    private final AtomicLong atomic;
    /**
     * 日志存放容器切换状态
     */
    private volatile boolean flag = true;

    public AbstractSysLogBatchService() {
        this(100);
    }

    public AbstractSysLogBatchService(int sysLogSize) {
        if (sysLogSize > 0) {
            this.sysLogSize = sysLogSize;
        } else {
            this.sysLogSize = 100;
        }
        this.sysLogList0 = Collections.synchronizedList(new ArrayList<SysLog>());
        this.sysLogList1 = Collections.synchronizedList(new ArrayList<SysLog>());
        this.atomic = new AtomicLong(0);
    }

    @Override
    public void setSysLogSize(int sysLogSize) {
        this.sysLogSize = sysLogSize;
    }

    /**
     * 可能是增删改的方法所包含的字符串，与数据源的事务对应
     */
    private final static String ADD_OPERATION = "^((insert)|(add)|(create)|(save)).*";
    private final static String DEL_OPERATION = "delete";
    private final static String EDIT_OPERATION = "update";

    /**
     * @Title: getLogType
     * @author: 吴佳隆
     * @data: 2019年7月3日 下午6:45:02
     * @Description: 获取请求操作类型（日志类型）
     * @return LogTypeEnum 日志操作类型枚举类
     */
    public LogTypeEnum getLogType(String methodName) {
        if (StringUtil.isNotBlank(methodName)) {
            if (methodName.matches(ADD_OPERATION)) {
                return LogTypeEnum.ADD;
            } else if (methodName.startsWith(DEL_OPERATION)) {
                return LogTypeEnum.DEL;
            } else if (methodName.startsWith(EDIT_OPERATION)) {
                return LogTypeEnum.EDIT;
            }
        }
        return LogTypeEnum.QUERY;
    }

    @Override
    public void insertSysLog(Object... objects) {
        this.insertSysLog(this.createSysLog(objects), atomic.incrementAndGet() % sysLogSize == 0);
    }

    /**
     * @Title: insertSysLog
     * @author: 吴佳隆
     * @data: 2019年7月3日 下午5:30:58
     * @Description: 向指定临时日志存放容器或数据库插入日志
     * @param log           日志对象
     * @param state         true：向数据库插入，false：向临时日志存放容器存放
     */
    private synchronized void insertSysLog(SysLog log, boolean state) {
        logger.debug("flag:{}, state:{}, sysLogSize:{}, sysLogList0.size:{}, sysLogList1.size:{}......", flag, state,
                sysLogSize, sysLogList0.size(), sysLogList1.size());
        if (flag) { // 使用第一个容器
            // 添加到临时容器
            sysLogList0.add(log);
            if (state && sysLogList1.isEmpty()) { // 批量插入到数据库
                // 改变容器
                flag = !flag;
                insertSysLog(sysLogList0);
            }
        } else { // 使用第二个容器
            // 添加到临时容器
            sysLogList1.add(log);
            if (state && sysLogList0.isEmpty()) { // 批量插入到数据库
                // 改变容器
                flag = !flag;
                insertSysLog(sysLogList1);
            }
        }
    }

    /**
     * @Title: insertSysLog
     * @author: 吴佳隆
     * @data: 2019年7月3日 下午5:32:21
     * @Description: 异步向数据库批量插入日志
     * @param sysLogList 日志列表
     */
    private void insertSysLog(List<SysLog> sysLogList) {
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    sysLogDao.insertBatch(sysLogList);
                } finally {
                    // 清理容器
                    sysLogList.clear();
                }
            }
        });
    }

    @Override
    public void insertSysLog() {
        if (!sysLogList0.isEmpty()) {
            sysLogDao.insertBatch(sysLogList0);
            sysLogList0.clear();
        }
        if (!sysLogList1.isEmpty()) {
            sysLogDao.insertBatch(sysLogList1);
            sysLogList1.clear();
        }
    }

}