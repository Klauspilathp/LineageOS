package top.d7c.springboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

/**
 * @Title: FlowableModelerApplication
 * @Package: top.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午12:47:26
 * @Description: flowable 工作流 modeler 设计器启动类。
 * http://127.0.0.1:8099/flowable-modeler
 */
@Import({org.flowable.ui.modeler.conf.DatabaseConfiguration.class,
        org.flowable.ui.modeler.conf.ApplicationConfiguration.class,
        org.flowable.ui.modeler.servlet.AppDispatcherServletConfiguration.class,})
@SpringBootApplication(scanBasePackages = {"top.d7c.springboot"})
public class FlowableModelerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FlowableModelerApplication.class, args);
    }

}
