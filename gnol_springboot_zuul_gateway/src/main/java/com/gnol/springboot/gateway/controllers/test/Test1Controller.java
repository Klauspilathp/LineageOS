package com.gnol.springboot.gateway.controllers.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;

/**
 * @Title: Test1Controller
 * @Package: com.gnol.springboot.gateway.controllers.test
 * @author: 吴佳隆
 * @date: 2020年6月22日 下午7:15:10
 * @Description: 测试服务聚合
 */
@RestController
@RequestMapping("/gateway/test1")
public class Test1Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test1Controller.class);
    @Autowired
    private RestTemplate restTemplate;

    /**
     * http://127.0.0.1:8080/gateway/test1/t1
     */
    @RequestMapping("/t1")
    public PageResult t1() {
        logger.info("gateway.t1");
        PageData pd = new PageData();
        ResponseEntity<PageResult> client1 = restTemplate
                .getForEntity("http://gnol-springboot-eureka-client1/client1/test1/t3", PageResult.class);
        pd.put("client1", client1.getBody().getData());
        PageResult client2 = restTemplate.getForObject("http://gnol-springboot-eureka-client2/client2/test1/t4",
                PageResult.class);
        pd.put("client2", client2.getData());
        return PageResult.ok(pd);
    }

}