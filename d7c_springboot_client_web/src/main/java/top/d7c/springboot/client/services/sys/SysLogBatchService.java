package top.d7c.springboot.client.services.sys;

import top.d7c.springboot.common.dos.sys.SysLog;

/**
 * @Title: SysLogBatchService
 * @Package: top.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年12月18日 下午2:02:05
 * @Description: 系统批量日志服务
 */
public interface SysLogBatchService {

    /**
     * @Title: setSysLogSize
     * @author: 吴佳隆
     * @data: 2020年3月20日 下午7:26:59
     * @Description: 设置每次插入日志数据
     * @param sysLogSize
     */
    void setSysLogSize(int sysLogSize);

    /**
     * @Title: createSysLog
     * @author: 吴佳隆
     * @data: 2019年12月11日 上午11:51:36
     * @Description: 生成日志对象
     * @param objects
     * @return SysLog
     */
    SysLog createSysLog(Object... objects);

    /**
     * @Title: insertSysLog
     * @author: 吴佳隆
     * @data: 2020年3月20日 上午8:19:34
     * @Description: 向容器或数据库中插入数据，对外调用
     * @param objects   生成日志对象
     */
    void insertSysLog(Object... objects);

    /**
     * @Title: insertSysLog
     * @author: 吴佳隆
     * @data: 2019年12月18日 下午2:13:39
     * @Description: 容器销毁时插入所有数据
     */
    void insertSysLog();

}