package com.gnol.springboot.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: GatewayApplication
 * @Package: com.gnol.springboot.gateway
 * @author: 吴佳隆
 * @date: 2020年6月27日 下午7:03:08
 * @Description: gateway 网关启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"com.gnol.springboot.auth.feigns"}) // 启用 Fegin
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}