package top.d7c.springboot.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import top.d7c.springboot.client.controlles.test.Test3Controller;

/**
 * @Title: Test3RouterConfiguration
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月7日 下午7:13:31
 * @Description: Test3Controller 路由配置
 */
@Configuration
public class Test3RouterConfiguration {

    /**
     * 通过浏览访问查看数据流 http://127.0.0.1:8094/test3/test1
     */
    @Bean
    public RouterFunction<ServerResponse> test1Test3ControllerRouterFunction(Test3Controller test3Controller) {
        return RouterFunctions.route(RequestPredicates.GET("/test3/test1"), test3Controller::test1);
    }

}