package top.d7c.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: WebSocketApplication
 * @Package: top.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:26:36
 * @Description: websocket 项目启动类
 */
@SpringBootApplication(scanBasePackages = {"top.d7c.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"top.d7c.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"top.d7c.springboot.common.daos", "top.d7c.springboot.client.daos"})
public class WebSocketApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebSocketApplication.class);
    }

}