package com.gnol.springboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: WebApplication
 * @Package: com.gnol.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:26:36
 * @Description: web 项目启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
/**
 * @EnableEurekaClient 注解请求 http://gnol-springboot-eureka-client1 服务时至少需要启动两个客户端服务，
 * 而 @EnableDiscoveryClient 注解只需启动一个服务
 */
@EnableDiscoveryClient
@EnableFeignClients // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApplication.class);
    }

}