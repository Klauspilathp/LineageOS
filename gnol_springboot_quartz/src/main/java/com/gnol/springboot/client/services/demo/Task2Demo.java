package com.gnol.springboot.client.services.demo;

import org.springframework.scheduling.annotation.Scheduled;

import com.gnol.plugins.tools.date.DateUtil;

/**
 * @Title: Task2Demo
 * @Package: com.gnol.springboot.client.services.demo
 * @author: 吴佳隆
 * @date: 2020年7月14日 下午8:05:02
 * @Description: 定时任务测试类
 */
public class Task2Demo {

    @Scheduled(initialDelay = 20000, fixedDelay = 10000)
    public void testTask() {
        System.out.println(DateUtil.getDateSecond());
    }

}