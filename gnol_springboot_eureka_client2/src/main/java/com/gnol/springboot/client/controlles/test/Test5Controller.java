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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @Title: Test5Controller
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午5:20:18
 * @Description: hystrix 注解
 */
@RestController
@RequestMapping("/test5")
public class Test5Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test5Controller.class);
    @Autowired
    private RestTemplate restTemplate;

    /**
     * http://gnol-springboot-eureka-client1/client1/test2/t1 请求正常
     * http://gnol-springboot-eureka-client1/client1/test2/t2 请求会被降级
     */
    @HystrixCommand(fallbackMethod = "getFallback", // 回调方法名
            groupKey = "client2Test5GroupKey", // 配置全局唯一标识的服务分组名称，相同分组的服务会聚合在一起，必填。
            commandKey = "test5Controller", // 配置全局唯一标识的服务名称，默认是简单类名。
            threadPoolKey = "client2Test5ThreadPoolKey", // 配置全局唯一标识的线程池名称，相同线程池名称会用一个线程池，默认是分组名，会作为线程池中线程名字的前缀。
            threadPoolProperties = { // 配置线程池参数
                    @HystrixProperty(name = "coreSize", value = "5"), // 核心线程池大小和线程池最大大小
                    @HystrixProperty(name = "maxQueueSize", value = "5"), // 线程池队列最大大小
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5") // 限定队列大小，即实际队列大小
            })
    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public PageResult t1(String id) {
        logger.info("client2.t1 param id : {}", id);
        return restTemplate.getForObject("http://gnol-springboot-eureka-client1/client1/test2/t2?id=" + id,
                PageResult.class);
    }

    // 服务降级时调用
    protected PageResult getFallback(String id) {
        return PageResult.error("id 为 " + id + " 的服务被降级");
    }

}