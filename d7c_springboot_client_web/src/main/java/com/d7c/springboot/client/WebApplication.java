package com.d7c.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: WebApplication
 * @Package: com.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:26:36
 * @Description: web 项目启动类
 */
@SpringBootApplication(scanBasePackages = {"com.d7c.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"com.d7c.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"com.d7c.springboot.common.daos", "com.d7c.springboot.client.daos"})
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApplication.class);
    }

}