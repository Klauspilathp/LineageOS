package top.d7c.springboot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Title: ConfigApplication
 * @Package: top.d7c.springboot.config
 * @author: 吴佳隆
 * @date: 2020年6月17日 下午3:48:36
 * @Description: 配置中心启动类
 */
@SpringBootApplication(scanBasePackages = {"top.d7c.springboot"})
@EnableConfigServer // 启动配置中心注解
@EnableEurekaClient // 开启 eureka 客户端
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}