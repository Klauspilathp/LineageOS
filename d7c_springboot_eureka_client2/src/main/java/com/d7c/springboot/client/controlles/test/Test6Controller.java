package com.d7c.springboot.client.controlles.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.PageResult;

/**
 * @Title: Test6Controller
 * @Package: com.d7c.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午6:23:49
 * @Description: Feign 整合 Hystrix
 */
@RestController
@RequestMapping("/test6")
public class Test6Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test6Controller.class);
    @Autowired
    private Client1Test2FeignClient feignClient;

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public PageResult t1(String id) {
        logger.info("client2.t1 param id : {}", id);
        return feignClient.t1(id);
    }

    @GetMapping(value = "/t2")
    public PageResult t2() {
        return feignClient.t2();
    }

}

/**
 * @Title: Client1Test2FeignClient
 * @Package: com.d7c.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 上午10:21:54
 * @Description: Feign 客户端
 */
@FeignClient(name = "d7c-springboot-client1", // 服务应用名
        path = "/test2", // 服务路径前缀
        fallback = Client1Test2FeignClientFallback.class // 回调函数类
// configuration = FeignDisableHystrixConfiguration.class // 为当前 Feign 禁用 Hystrix 功能
)
interface Client1Test2FeignClient extends Client1Test2Mapping {

}

/**
 * @Title: Client1Test2Mapping
 * @Package: com.d7c.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 上午10:23:02
 * @Description: client1 的 test2 类服务接口
 */
interface Client1Test2Mapping {

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    PageResult t1(@RequestParam("id") String id);

    @GetMapping("/t2")
    PageResult t2();

}

/**
 * @Title: Client1Test2FeignClientFallback
 * @Package: com.d7c.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午7:04:53
 * @Description: Hystrix 降级方法
 */
@Component
class Client1Test2FeignClientFallback implements Client1Test2FeignClient {

    @Override
    public PageResult t1(String id) {
        return PageResult.error("id 为 " + id + " 的 t1 服务被降级了！！！");
    }

    @Override
    public PageResult t2() {
        return PageResult.error("t2 服务被降级了！！！");
    }

}