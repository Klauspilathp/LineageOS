package com.gnol.springboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Title: EurekaClient2Application
 * @Package: com.gnol.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月12日 上午8:11:59
 * @Description: eureka client 启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableDiscoveryClient
public class EurekaClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient2Application.class, args);
    }

}