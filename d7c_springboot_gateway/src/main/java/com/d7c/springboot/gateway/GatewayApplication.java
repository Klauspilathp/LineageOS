package com.d7c.springboot.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: GatewayApplication
 * @Package: com.d7c.springboot.gateway
 * @author: 吴佳隆
 * @date: 2020年6月27日 下午7:03:08
 * @Description: gateway 网关启动类
 * 查询所有路由规则 GET：http://127.0.0.1:8081/actuator/gateway/routes
 * 查询指定 id 路由规则 GET：http://127.0.0.1:8081/actuator/gateway/routes/d7c-springboot-auth
 * 刷新路由规则 POST：http://127.0.0.1:8081/actuator/gateway/refresh
 * 查询路由过滤器 GET：http://127.0.0.1:8081/actuator/gateway/routefilters
 * 查询全局过滤器 GET：http://127.0.0.1:8081/actuator/gateway/globalfilters
 */
@SpringBootApplication(scanBasePackages = {"com.d7c.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"com.d7c.springboot.auth.feigns"}) // 启用 Fegin
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}