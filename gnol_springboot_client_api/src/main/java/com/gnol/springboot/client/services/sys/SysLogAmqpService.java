package com.gnol.springboot.client.services.sys;

/**
 * @Title: SysLogAmqpService
 * @Package: com.gnol.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2020年7月28日 下午7:08:51
 * @Description: amqp 方式实现的日志插入服务接口
 */
public interface SysLogAmqpService {
    /**
     * 系统日志队列名称
     */
    public static final String SYS_LOG_QUEUE = "sys.log.queue";

    /**
     * @Title: insertSysLog
     * @author: 吴佳隆
     * @data: 2020年3月20日 上午8:19:34
     * @Description: 向容器或数据库中插入数据，对外调用
     * @param objects   生成日志对象
     */
    void insertSysLog(Object... objects);

}