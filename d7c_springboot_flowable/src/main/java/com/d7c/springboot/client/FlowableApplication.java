package com.d7c.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: FlowableApplication
 * @Package: com.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2021年4月22日 下午8:16:54
 * @Description: flowable 工作流 client 启动类
 */
@SpringBootApplication(scanBasePackages = {"com.d7c.springboot"})
/**
 * @EnableEurekaClient 注解请求 eureka 客户端服务时要求至少启动两个客户端服务，
 * 而 @EnableDiscoveryClient 注解只需启动一个服务
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.d7c.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"com.d7c.springboot.common.daos", "com.d7c.springboot.client.daos"})
public class FlowableApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }

}
