package top.d7c.springboot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Title: EurekaServerApplication
 * @Package: top.d7c.springboot.server
 * @author: 吴佳隆
 * @date: 2020年6月11日 下午7:23:03
 * @Description: eureka server 启动类
 * 访问 http://127.0.0.1:9000/ 查看 eureka 注册中心
 */
@SpringBootApplication(scanBasePackages = {"top.d7c.springboot"})
@EnableEurekaServer // eureka 服务端注解
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}