package com.gnol.springboot.client.controllers.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageResult;

/**
 * @Title: Test1Controller
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月17日 上午9:22:10
 * @Description: 测试服务发现
 */
@RestController
@RequestMapping("/test1")
public class Test1Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test1Controller.class);
    @Autowired
    private Registration registration;

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public PageResult t1(String id) {
        logger.info("client1.t1 param id : {}", id);
        return PageResult.ok(new StringBuilder().append("id 为").append(id).append(" 的用户是吴佳隆！！！"));
    }

    @GetMapping("/t2/{id}")
    public PageResult t2(@PathVariable("id") String id) {
        logger.info("client1.t2 param id : {}", id);
        return PageResult.ok(new StringBuilder().append("id 为 ").append(id).append(" 的用户是吴佳隆！！！"));
    }

    @GetMapping("/t3")
    public PageResult t3() {
        logger.info("client1.t3 registration {} : {}", registration.getHost(), registration.getPort());
        return PageResult
                .ok(new StringBuilder().append(registration.getHost()).append(" : ").append(registration.getPort()));
    }

}