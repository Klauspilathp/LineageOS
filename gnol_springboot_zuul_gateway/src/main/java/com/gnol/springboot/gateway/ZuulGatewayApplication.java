package com.gnol.springboot.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: ZuulGatewayApplication
 * @Package: com.gnol.springboot.gateway
 * @author: 吴佳隆
 * @date: 2020年6月11日 下午7:23:03
 * @Description: zuul gateway 启动类，http://127.0.0.1:9080/
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
/**
 * @EnableEurekaClient 注解请求 http://gnol-springboot-eureka-client1 服务时至少需要启动两个客户端服务，
 * 而 @EnableDiscoveryClient 注解只需启动一个服务
 */
@EnableDiscoveryClient
@EnableFeignClients // 启用 Fegin
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }

}