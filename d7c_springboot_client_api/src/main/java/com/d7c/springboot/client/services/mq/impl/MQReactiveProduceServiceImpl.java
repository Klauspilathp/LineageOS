package com.d7c.springboot.client.services.mq.impl;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import org.springframework.stereotype.Service;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.tools.date.DateUtil;
import com.d7c.plugins.tools.idfactory.IdFactory;
import com.d7c.springboot.client.services.mq.OutputChannel;

import reactor.core.publisher.Flux;

/**
 * @Title: MQReactiveProduceServiceImpl
 * @Package: com.d7c.springboot.client.services.mq.impl
 * @author: 吴佳隆
 * @date: 2021年1月7日 上午11:01:37
 * @Description: MQ 响应式编程消息生产者实现。
 * https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/#spring-cloud-stream-overview-reactive-programming-support
 */
@Service(value = "mQReactiveProduceServiceImpl")
@EnableBinding(value = {OutputChannel.class})
public class MQReactiveProduceServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MQReactiveProduceServiceImpl.class);

    /**
     * 发送响应式，每隔两秒发送一条消息
     */
    @StreamEmitter
    @Output(OutputChannel.OUTPUT)
    public Flux<PageData> test1() {
        return Flux.interval(Duration.ofSeconds(2)).map(mapper -> {
            PageData pd = PageData.build();
            pd.put("id", IdFactory.nextStr());
            pd.put("method", "MQReactiveProduceServiceImpl.test1");
            pd.put("addTime", DateUtil.getDateSecond());
            logger.info("Send to {} message [{}].", OutputChannel.OUTPUT, pd.toString());
            return pd;
        });
    }

}