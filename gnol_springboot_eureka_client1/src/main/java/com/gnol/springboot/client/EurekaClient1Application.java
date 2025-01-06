package com.gnol.springboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Title: EurekaClient1Application
 * @Package: com.gnol.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月12日 上午8:12:06
 * @Description: eureka client 启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableEurekaClient
public class EurekaClient1Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient1Application.class, args);
    }

}