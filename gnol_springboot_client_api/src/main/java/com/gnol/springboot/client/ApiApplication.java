package com.gnol.springboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: ApiApplication
 * @Package: com.gnol.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月7日 下午8:08:30
 * @Description: api 服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
/**
 * @EnableEurekaClient 注解请求 http://gnol-springboot-eureka-client1 服务时至少需要启动两个客户端服务，
 * 而 @EnableDiscoveryClient 注解只需启动一个服务
 */
@EnableDiscoveryClient
@EnableFeignClients // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}