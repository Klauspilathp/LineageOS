package com.gnol.springboot.client.services.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gnol.plugins.tools.date.DateUtil;

/**
 * @Title: Task2Demo
 * @Package: com.gnol.springboot.client.services.demo
 * @author: 吴佳隆
 * @date: 2020年7月14日 下午8:05:02
 * @Description: 定时任务测试类
 */
@Service(value = "task2Demo")
@Lazy(false)
public class Task2Demo {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(initialDelay = 20000, fixedDelay = 10000)
    public void testTask() {
        logger.info("Task2Demo.testTask=======>" + DateUtil.getDateSecond());
    }

}