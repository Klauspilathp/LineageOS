package com.gnol.springboot.client.controlles.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gnol.plugins.core.PageResult;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @Title: Test3Controller
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午2:33:14
 * @Description: hystrix 测试
 */
@RestController
@RequestMapping("/test3")
public class Test3Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test3Controller.class);
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public Object t1(String id) {
        logger.info("client2.t1 param id : {}", id);
        return new Test3HystrixCommand("test3CommandGroupKey", restTemplate, id).execute();
    }

    @GetMapping("/t2")
    public PageResult t2() {
        return new Test3HystrixCommand("test3CommandGroupKey", restTemplate, "").execute();
    }

}

/**
 * @Title: Test3HystrixCommand
 * @Package: com.gnol.springboot.client.controlles.test
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午3:16:28
 * @Description: 通过命令模式的方式，继承 HystrixCommand 类来包裹具体的服务调用逻辑（run 方法），并在命令模式中添加服务调用失败后的降级逻辑（getFallback 方法）。
 */
class Test3HystrixCommand extends HystrixCommand<PageResult> {
    private RestTemplate restTemplate;
    private String id;

    public Test3HystrixCommand(String commandGroupKey, RestTemplate restTemplate, String id) {
        /*super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("test3GroupKey"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("test3CommandKey"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("test3ThreadPoolKey")));*/
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    // 服务正常调用
    @Override
    protected PageResult run() throws Exception {
        return restTemplate.getForObject("http://gnol-springboot-client1/test2/t2?id=" + id, PageResult.class);
    }

    // 服务降级时调用
    @Override
    protected PageResult getFallback() {
        return PageResult.error("服务被降级");
    }

}