package com.gnol.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: AuthApplication
 * @Package: com.gnol.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月15日 上午9:52:39
 * @Description: 认证服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"com.gnol.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"com.gnol.springboot.common.daos", "com.gnol.springboot.client.daos"})
public class AuthApplication {

    /**
     * 本项目有两套认证方式：
     * 第一套：
     *    注释掉 com.gnol.springboot.client.config.ClusterWebSecurityConfiguration 类上的注解，
     * 采用 com.gnol.springboot.client.controllers.AuthController 里的三个控制方法实现请求的认证、校验和注销等。
     * 第二套：
     *    直接打开 com.gnol.springboot.client.config.ClusterWebSecurityConfiguration 类上的注解，利用 security 内部的 login、logout 控制方法实现
     * 请求的认证、校验和注销等。
     * 认证方法：采用 POST 请求 content Type 必须为 application/x-www-form-urlencoded，传入 username、password、auth_type。
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}