package top.d7c.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: ActivitiApplication
 * @Package: top.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月12日 上午8:12:06
 * @Description: activiti 工作流 client 启动类
 */
@SpringBootApplication(scanBasePackages = {"top.d7c.springboot"})
/**
 * @EnableEurekaClient 注解请求 eureka 客户端服务时要求至少启动两个客户端服务，
 * 而 @EnableDiscoveryClient 注解只需启动一个服务
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"top.d7c.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"top.d7c.springboot.common.daos", "top.d7c.springboot.client.daos"})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

}