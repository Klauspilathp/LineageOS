package com.d7c.springboot.client.controllers.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.PageResult;

/**
 * @Title: Test2Controller
 * @Package: com.d7c.springboot.client.controllers.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午2:39:00
 * @Description: 模拟服务超时、故障等
 */
@RestController
@RequestMapping("/test2")
public class Test2Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test1Controller.class);
    @Autowired
    private Registration registration;

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public PageResult t1(String id) {
        logger.info("client2.t1 id:{} registration {} : {}", id, registration.getHost(), registration.getPort());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return PageResult.ok(new StringBuilder().append("t1 ").append(registration.getHost()).append(" : ")
                .append(registration.getPort()));
    }

    @GetMapping("/t2")
    public PageResult t2() {
        logger.info("client2.t2 registration {} : {}", registration.getHost(), registration.getPort());
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return PageResult.ok(new StringBuilder().append("t2 ").append(registration.getHost()).append(" : ")
                .append(registration.getPort()));
    }

}