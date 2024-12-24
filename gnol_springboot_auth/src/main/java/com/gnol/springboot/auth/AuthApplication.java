package com.gnol.springboot.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: AuthApplication
 * @Package: com.gnol.springboot.auth
 * @author: 吴佳隆
 * @date: 2020年6月15日 上午9:52:39
 * @Description: 认证服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}