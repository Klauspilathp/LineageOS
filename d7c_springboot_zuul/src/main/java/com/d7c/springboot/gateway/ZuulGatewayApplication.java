package com.d7c.springboot.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: ZuulGatewayApplication
 * @Package: com.d7c.springboot.gateway
 * @author: 吴佳隆
 * @date: 2020年6月11日 下午7:23:03
 * @Description: zuul gateway 启动类
 * http://127.0.0.1:8080/
 * 整合 hystrix：http://127.0.0.1:8080/hystrix.stream
 * zuul 的路由规则：http://127.0.0.1:8080/actuator/routes
 */
@SpringBootApplication(scanBasePackages = {"com.d7c.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"com.d7c.springboot.auth.feigns"}) // 启用 Fegin
@EnableZuulProxy // zuul
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }

}