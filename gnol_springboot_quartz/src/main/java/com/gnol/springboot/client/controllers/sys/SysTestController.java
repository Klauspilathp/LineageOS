package com.gnol.springboot.client.controllers.sys;

import org.springframework.scheduling.annotation.Scheduled;

import com.gnol.plugins.tools.date.DateUtil;

/**
 * @Title: SysTestController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月14日 下午8:05:02
 * @Description: 定时任务测试类
 */
public class SysTestController {

    @Scheduled(initialDelay = 20000, fixedDelay = 10000)
    public void sendMsg() {
        System.out.println(DateUtil.getDateSecond());
    }

}