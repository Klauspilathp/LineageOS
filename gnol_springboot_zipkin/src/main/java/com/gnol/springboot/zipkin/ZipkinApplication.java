package com.gnol.springboot.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: ZipkinApplication
 * @Package: com.gnol.springboot.zipkin
 * @author: 吴佳隆
 * @date: 2020年6月24日 下午7:20:07
 * @Description: zipkin 调用链启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
public class ZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }

}