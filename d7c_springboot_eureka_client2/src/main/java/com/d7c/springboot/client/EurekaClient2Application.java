package com.d7c.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Title: EurekaClient2Application
 * @Package: com.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月12日 上午8:11:59
 * @Description: eureka client 启动类
 */
@SpringBootApplication(scanBasePackages = {"com.d7c.springboot"})
/**
 * @EnableEurekaClient 注解请求 http://d7c-springboot-client1 服务时至少需要启动两个客户端服务，
 * 而 @EnableDiscoveryClient 注解只需启动一个服务
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.d7c.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"com.d7c.springboot.common.daos", "com.d7c.springboot.client.daos"})
@EnableJms // 启用 jms 支持
@EnableScheduling // 启用定时任务
public class EurekaClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient2Application.class, args);
    }

}