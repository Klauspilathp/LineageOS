package top.d7c.springboot.client.services.sys.impl;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import top.d7c.springboot.client.services.sys.SysLogAmqpService;
import top.d7c.springboot.common.dos.sys.SysLog;

/**
 * @Title: SysLogAmqpServiceImpl
 * @Package: top.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2020年7月28日 下午7:05:06
 * @Description: amqp 方式实现的日志插入服务实现
 */
@Service(value = "sysLogAmqpServiceImpl")
public class SysLogAmqpServiceImpl implements SysLogAmqpService {
    private static final Logger logger = LoggerFactory.getLogger(SysLogAmqpServiceImpl.class);
    /**
     * amqp 模板
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Bean("sysLogQueue")
    @ConditionalOnMissingBean(name = "sysLogQueue")
    public Queue sysLogQueue() {
        return new Queue(SYS_LOG_QUEUE);
    }

    @Override
    public void insertSysLog(Object... objects) {
        CompletableFuture.runAsync(() -> {
            try {
                SysLog log = new SysLog();

                amqpTemplate.convertAndSend(SYS_LOG_QUEUE, log);
                logger.info("发送日志到队列");
            } catch (AmqpException e) {
                e.printStackTrace();
            }
        });
    }

}