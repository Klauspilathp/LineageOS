package com.d7c.springboot.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.d7c.springboot.client.controlles.test.Test1Controller;

/**
 * @Title: Test1RouterConfiguration
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月7日 下午5:56:05
 * @Description: Test1Controller 路由配置
 */
@Configuration
public class Test1RouterConfiguration {

    /**
     * 将一个 GET 请求 /test1/test1 路由到处理器 Test1Controller 的 test1() 方法上。
     * http://127.0.0.1:8094/test1/test1
     */
    @Bean
    public RouterFunction<ServerResponse> test1Test1ControllerRouterFunction(Test1Controller test1Controller) {
        return RouterFunctions.route(
                RequestPredicates.GET("/test1/test1").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                test1Controller::test1);
    }

    /**
     * http://127.0.0.1:8094/test1/test2
     * http://127.0.0.1:8094/test1/test3
     */
    @Bean
    public RouterFunction<ServerResponse> test23Test1ControllerRouterFunction(Test1Controller test1Controller) {
        return RouterFunctions.route(RequestPredicates.GET("/test1/test2"), request -> test1Controller.test2(request))
                .andRoute(RequestPredicates.GET("/test1/test3").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        test1Controller::test3);
    }

}