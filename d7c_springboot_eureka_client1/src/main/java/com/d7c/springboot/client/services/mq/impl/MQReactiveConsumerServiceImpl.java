package com.d7c.springboot.client.services.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.tools.date.DateUtil;
import com.d7c.springboot.client.services.mq.InputChannel;
import com.d7c.springboot.client.services.mq.OrderLogChannel;

import reactor.core.publisher.Flux;

/**
 * @Title: MQReactiveConsumerServiceImpl
 * @Package: com.d7c.springboot.client.services.mq.impl
 * @author: 吴佳隆
 * @date: 2021年1月7日 上午11:12:54
 * @Description: MQ 响应式编程消息消费者实现。
 * https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/#spring-cloud-stream-overview-reactive-programming-support
 */
@EnableBinding(value = {InputChannel.class, OrderLogChannel.class})
public class MQReactiveConsumerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MQReactiveConsumerServiceImpl.class);

    /**
     * 响应式编程方式，消息聚合三次才被转发到 {@link OrderLogChannel.OUTPUT} 通道。
     */
    @StreamListener(value = InputChannel.INPUT)
    @Output(OrderLogChannel.OUTPUT)
    public Flux<String> consumer1(Flux<PageData> input) {
        return input.map(pd -> {
            if (pd != null) {
                pd.put("consumer1", DateUtil.getDateSecond());
            }
            return pd;
        }).log() // 打印日志
                .buffer(3) // 每聚合三条向 {@link OrderLogChannel.OUTPUT} 通道转发一次
                .map(agg -> "consumer1 transpond : " + agg);
    }

    /**
     * 监听反馈
     */
    @StreamListener(value = OrderLogChannel.INPUT)
    public void orderLog(Flux<String> flux) {
        flux.subscribe(log -> logger.info("orderLog [{}]", log));
    }

}