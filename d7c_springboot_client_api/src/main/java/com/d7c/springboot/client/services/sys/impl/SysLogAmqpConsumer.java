package com.d7c.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.d7c.springboot.client.services.sys.SysLogAmqpService;
import com.d7c.springboot.common.daos.sys.BaseSysLogDao;
import com.d7c.springboot.common.dos.sys.SysLog;

/**
 * @Title: SysLogAmqpConsumer
 * @Package: com.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2020年7月28日 下午7:46:30
 * @Description: amqp 日志消费者服务
 */
@Component
@RabbitListener(queues = SysLogAmqpService.SYS_LOG_QUEUE) // 监听系统日志队列
public class SysLogAmqpConsumer {
    /**
     * d7c 系统日志基础 Dao
     */
    @Resource(name = "baseSysLogDao")
    private BaseSysLogDao sysLogDao;

    @RabbitHandler
    public void logHandler(SysLog log) {
        // sysLogDao.insert(log);
    }

}