package com.gnol.springboot.zuul.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: ZuulGatewayApplication
 * @Package: com.gnol.springboot.zuul.gateway
 * @author: 吴佳隆
 * @date: 2020年6月11日 下午7:23:03
 * @Description: zuul gateway 启动类，http://127.0.0.1:9080/
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }

}