package top.d7c.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Title: QuartzApplication
 * @Package: top.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:26:36
 * @Description: quartz 项目启动类
 */
@SpringBootApplication(scanBasePackages = {"top.d7c.springboot"})
@MapperScan(basePackages = {"top.d7c.springboot.client.daos"})
@EnableScheduling // 启用定时任务
public class QuartzApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(QuartzApplication.class);
    }

}