package com.gnol.springboot.client.controllers.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.tools.idfactory.IdFactory;
import com.gnol.springboot.client.services.mq.impl.MQProducerServiceImpl;

/**
 * @Title: MQProducerController
 * @Package: com.gnol.springboot.client.controllers.mq
 * @author: 吴佳隆
 * @date: 2021年1月5日 上午11:41:11
 * @Description: 生产消息控制类
 * https://github.com/spring-cloud/spring-cloud-stream/tree/2.1.x
 */
@RestController
@RequestMapping(value = "/mq/MQProducer")
public class MQProducerController {
    @Autowired
    private MQProducerServiceImpl producerService;

    @GetMapping(value = "/test1")
    public PageResult test1() {
        PageData pd = PageData.build();
        pd.put("id", IdFactory.nextStr());
        pd.put("name", "吴佳隆");
        pd.put("age", 18);
        pd.put("addTime", "1970-01-01");
        producerService.test0(pd);
        return PageResult.ok(pd.toString());
    }

}