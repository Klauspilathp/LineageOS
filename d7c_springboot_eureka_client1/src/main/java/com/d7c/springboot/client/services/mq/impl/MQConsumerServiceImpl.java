package com.d7c.springboot.client.services.mq.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.tools.date.DateUtil;
import com.d7c.springboot.client.services.mq.InputChannel;
import com.d7c.springboot.client.services.mq.OrderLogChannel;

/**
 * @Title: MQConsumerServiceImpl
 * @Package: com.d7c.springboot.client.services.mq.impl
 * @author: 吴佳隆
 * @date: 2021年1月5日 上午10:09:45
 * @Description: MQ 消费者实现，生产者参见 /d7c_springboot_client_api/src/main/java/com/d7c/springboot/client/services/test/。
 * https://github.com/spring-cloud/spring-cloud-stream/tree/2.1.x
 */
@EnableBinding(value = {InputChannel.class, OrderLogChannel.class}) // 该注解用来指定一个或多个定义了 {@link @Input} 或 {@link @Output} 注解的接口，以此启动应用消息驱动并实现对消息通道的绑定。
public class MQConsumerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumerServiceImpl.class);

    /**
     * 从 {@link InputChannel.INPUT} 通道接收消息，然后向 {@link OrderLogChannel.OUTPUT} 通道发送一条消息。
     * <p/>
     * Spring Cloud Stream 是基于 Spring Integration 构建起来的， 所以在使用 SpringCloud Stream 构建消息驱动服务的时候，
     * 完全可以使用 Spring Integration 的原生注解来实现各种业务需求。
     * <p/>
     * {@link @ServiceActivator} 注解是 spring Integration 的原生支持。
     */
    @ServiceActivator(inputChannel = InputChannel.INPUT, outputChannel = OrderLogChannel.OUTPUT // 指定反馈消息的输出通道。
    )
    public PageData test0(@Payload PageData msg, @SuppressWarnings("rawtypes") @Headers Map headers) {
        logger.info(headers.get("contentType").toString());
        logger.info("test0 received from {} message: [{}].", InputChannel.INPUT, msg.toString());
        return msg;
    }

    /**
     * 使用 {@link @ServiceActivator} 注解接收消息前可以对消息进行处理。
     */
    @Transformer(inputChannel = InputChannel.INPUT, outputChannel = InputChannel.INPUT)
    public Object transform(PageData msg) {
        msg.put("consumerTransformTime", DateUtil.getDateSecond());
        logger.info("consumer transform message [{}].", msg.toString());
        return msg;
    }

    /**
     * 从 {@link InputChannel.INPUT} 通道接收消息，然后向 {@link OrderLogChannel.OUTPUT} 通道发送一条消息。
     * <p/>
     * 为了简化面向消息的编程模型，Spring Cloud Stream 提供了 {@link @StreamListener} 注解对输入通道做了进一步的优化。
     * <p/>
     * {@link @StreamListener} 该注解比 {@link @ServiceActivator} 注解强大，在处理消息优先级上是等效的，即一个消息只能被其中一个处理。
     */
    @StreamListener(value = InputChannel.INPUT // 该方法监听的通道
    ) // 将监听指定通道的被修饰方法注册为消息中间件上数据流的事件监听器。
    @SendTo(OrderLogChannel.OUTPUT) // 指定反馈消息的输出通道。
    public PageData test1(PageData msg, @SuppressWarnings("rawtypes") @Headers Map headers) {
        logger.info(headers.get("contentType").toString());
        logger.info("test1 received from {} message: [{}].", InputChannel.INPUT, msg.toString());
        return msg;
    }

    /**
     * 从 {@link OrderLogChannel.INPUT} 通道接收消息。
     */
    @StreamListener(value = OrderLogChannel.INPUT // 该方法监听的通道
    ) // 将监听指定通道的被修饰方法注册为消息中间件上数据流的事件监听器。
    public void orderLog(PageData msg, @SuppressWarnings("rawtypes") @Headers Map headers) {
        logger.info(headers.get("contentType").toString());
        logger.info("orderLog received from {} message: [{}].", OrderLogChannel.INPUT, msg.toString());
    }

}