package com.gnol.springboot.eureka.client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Title: EurekaClient1Application
 * @Package: com.gnol.springboot.eureka.client1
 * @author: 吴佳隆
 * @date: 2020年6月12日 上午8:12:06
 * @Description: eureka client 启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableDiscoveryClient
public class EurekaClient1Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient1Application.class, args);
    }

}