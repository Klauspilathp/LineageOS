package com.d7c.springboot.client.services.demo;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Title: Task1Demo
 * @Package: com.d7c.springboot.client.services.demo
 * @author: 吴佳隆
 * @date: 2019年7月4日 下午12:47:52
 * @Description: 定时任务测试
 */
@Service(value = "task1Demo")
@Lazy(false)
public class Task1Demo {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private Scheduler scheduler;

    /**
     * cron：cron 表达式；
     * initialDelay：第一次运行前需要延迟的时间，单位是毫秒；
     * fixedDelay：表示从上一个任务完成到下一个任务开始的时间间隔，单位是毫秒；
     * fixedRate：表示从上一个任务开始到下一个任务开始的时间间隔，单位是毫秒（如果某次任务开始时上次任务还没有结束，那么在上次任务执行完成时，当前任务会立即执行）。
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void testTask() {
        logger.info("Task1Demo.testTask=======>定时任务测试...");
        logger.info("scheduler：{}", scheduler);
    }

}