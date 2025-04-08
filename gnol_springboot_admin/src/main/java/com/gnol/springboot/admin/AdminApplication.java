package com.gnol.springboot.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @Title: ConfigApplication
 * @Package: com.gnol.springboot.admin
 * @author: 吴佳隆
 * @date: 2020年6月17日 下午3:48:36
 * @Description: 管理中心启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableEurekaClient
@EnableTurbine // turbine
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}