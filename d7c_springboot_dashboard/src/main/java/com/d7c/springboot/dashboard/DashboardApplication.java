package com.d7c.springboot.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @Title: DashboardApplication
 * @Package: com.d7c.springboot.dashboard
 * @author: 吴佳隆
 * @date: 2020年6月21日 下午6:42:10
 * @Description: dashboard 监控模块启动类
 */
@SpringBootApplication(scanBasePackages = {"com.d7c.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableHystrixDashboard // dashboard
@EnableTurbine // turbine
public class DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

}