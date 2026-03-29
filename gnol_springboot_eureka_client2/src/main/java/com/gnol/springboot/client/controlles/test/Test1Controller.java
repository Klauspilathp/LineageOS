package com.gnol.springboot.client.controlles.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gnol.plugins.core.PageResult;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

/**
 * @Title: Test1Controller
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月17日 上午9:22:10
 * @Description: 测试服务发现
 */
@RestController
@RequestMapping("/client2/test1")
@RefreshScope
public class Test1Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test1Controller.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private EurekaClient eurekaClient;

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
     * http://127.0.0.1:8093/client2/test1/t2?id=2 需要开启 com.gnol.springboot.client.config.RestTemplateConfig.restTemplate() 方法上的 @LoadBalanced 注解
     */
    @RequestMapping("/t2")
    public Object t2(String id) {
        String url = "http://gnol-springboot-eureka-client1/client1/test1/t1?id=" + id;
        logger.info("client2.t2 param id : {}, request url : {}", id, url);
        Object result = restTemplate.getForEntity(url, Object.class);
        return result;
    }

    @RequestMapping("/t3")
    public PageResult t3() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("gnol-springboot-eureka-client1");
        logger.info("client1.t3 {} : {} : {}", serviceInstance.getServiceId(), serviceInstance.getHost(),
                serviceInstance.getPort());
        return PageResult.ok(new StringBuilder().append(serviceInstance.getServiceId()).append(" : ")
                .append(serviceInstance.getHost()).append(" : ").append(serviceInstance.getPort()));
    }

    @RequestMapping("/t4")
    public PageResult t4() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("gnol-springboot-eureka-client1", false);
        return PageResult.ok(instance.getHomePageUrl());
    }

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/t5")
    public String t5() {
        return serverPort;
    }

}