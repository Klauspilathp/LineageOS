package com.gnol.springboot.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Title: EurekaServerApplication
 * @Package: com.gnol.springboot.eureka.server
 * @author: 吴佳隆
 * @date: 2020年6月11日 下午7:23:03
 * @Description: eureka server 启动类，http://127.0.0.1:9080/
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}