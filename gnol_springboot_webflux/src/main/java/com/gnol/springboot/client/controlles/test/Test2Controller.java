package com.gnol.springboot.client.controlles.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * @Title: Test2Controller
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年7月7日 下午6:39:41
 * @Description: webflux 模仿 web
 */
@RestController
@RequestMapping("/test2")
public class Test2Controller {

    /**
     * http://127.0.0.1:8094/test2/test1
     */
    @GetMapping("/test1")
    public Mono<String> test1() {
        return Mono.just("Test2Controller.test1");
    }

}