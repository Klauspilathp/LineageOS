package top.d7c.springboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: WebfluxApplication
 * @Package: top.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2020年7月7日 下午4:19:58
 * @Description: webflux 异步处理启动类
 * https://blog.csdn.net/get_set/article/details/79480233
 */
@SpringBootApplication(scanBasePackages = {"top.d7c.springboot"})
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

}