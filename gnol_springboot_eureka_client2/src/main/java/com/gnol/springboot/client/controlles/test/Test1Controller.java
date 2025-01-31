package com.gnol.springboot.client.controlles.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    /**
     * http://127.0.0.1:8093/client2/test1/t1?id=2 需要关闭 com.gnol.springboot.client.config.RestTemplateConfig.restTemplate() 方法上的 @LoadBalanced 注解
     */
    @RequestMapping("/t1")
    public Object t1(String id) {
        String url = "http://localhost:8092/client1/test1/t1?id=" + id;
        logger.info("client2.t1 param id : {}, request url : {}", id, url);
        Object result = restTemplate.getForEntity(url, Object.class);
        return result;
    }

    /**
     * http://127.0.0.1:8093/client2/test1/t2?id=2 需要开启 com.gnol.springboot.client.config.RestTemplateConfig.restTemplate() 方法上的 @LoadBalanced 注解，
     * 并启动至少两个 gnol-springboot-eureka-client1 服务。
     */
    @RequestMapping("/t2")
    public Object t2(String id) {
        String url = "http://gnol-springboot-eureka-client1/client1/test1/t1?id=" + id;
        logger.info("client2.t2 param id : {}, request url : {}", id, url);
        Object result = restTemplate.getForEntity(url, Object.class);
        return result;
    }

}