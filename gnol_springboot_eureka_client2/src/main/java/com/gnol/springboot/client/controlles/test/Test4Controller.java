package com.gnol.springboot.client.controlles.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gnol.plugins.core.PageResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @Title: Test4Controller
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午4:51:42
 * @Description: hystrix 注解模式测试
 */
@RestController
@RequestMapping("/test4")
public class Test4Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test4Controller.class);
    @Autowired
    private RestTemplate restTemplate;

    /**
     * http://gnol-springboot-client1/test2/t1 请求正常
     * http://gnol-springboot-client1/test2/t2 请求会被降级
     */
    @HystrixCommand(fallbackMethod = "getFallback")
    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public PageResult t1(String id) {
        logger.info("client2.t1 param id : {}", id);
        return restTemplate.getForObject("http://gnol-springboot-client1/test2/t1?id=" + id, PageResult.class);
    }

    // 服务降级时调用
    protected PageResult getFallback(String id) {
        return PageResult.error("id 为 " + id + " 的服务被降级");
    }

}