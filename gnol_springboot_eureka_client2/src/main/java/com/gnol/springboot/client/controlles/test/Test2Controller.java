package com.gnol.springboot.client.controlles.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageResult;

/**
 * @Title: Test2Controller
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 上午9:43:07
 * @Description: 测试 feign
 */
@RestController
@RequestMapping("/test2")
public class Test2Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test2Controller.class);
    @Autowired
    private Client1Test1FeignClient feignClient;

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public Object t1(String id) {
        logger.info("client2.t1 param id : {}", id);
        return feignClient.t1(id);
    }

    @GetMapping("/t2/{id}")
    public PageResult t2(@PathVariable("id") String id) {
        logger.info("client2.t2 param id : {}", id);
        return feignClient.t2(id);
    }

    @GetMapping(value = "/t3")
    public Object t3() {
        return feignClient.t3();
    }

}

/**
 * @Title: Client1Test1FeignClient
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 上午10:21:54
 * @Description: Feign 客户端
 */
@FeignClient(name = "gnol-springboot-client1")
interface Client1Test1FeignClient extends Client1Test1Mapping {

}

/**
 * @Title: Client1Test1Mapping
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 上午10:23:02
 * @Description: client1 的 test1 类服务接口
 */
@RequestMapping(path = "/test1")
interface Client1Test1Mapping {

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    PageResult t1(@RequestParam("id") String id);

    @GetMapping("/t2/{id}")
    PageResult t2(@PathVariable("id") String id);

    @GetMapping("/t3")
    PageResult t3();

}