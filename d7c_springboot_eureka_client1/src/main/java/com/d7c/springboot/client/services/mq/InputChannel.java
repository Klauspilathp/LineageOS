package com.d7c.springboot.client.services.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Title: InputChannel
 * @Package: com.d7c.springboot.client.services.mq
 * @author: 吴佳隆
 * @date: 2021年1月5日 上午10:06:15
 * @Description: 消息输入（消费）通道。可以参考 {@link org.springframework.cloud.stream.messaging.Sink} 写。
 * 想同时定义一个消息输入（消费）通道和一个消息输出（生产）通道可以参考 {@link org.springframework.cloud.stream.messaging.Processor} 写。
 */
public interface InputChannel {
    /**
     * 消息输入（消费）通道名称。
     */
    public static final String INPUT = "inputOrder";

    /**
     * {@link SubscribableChannel} 该接口定义了从消息通道接收消息的方法。
     * <p/>
     * 如果使用 {@link @Input} 注解而没有指定具体的 value 名称，将默认使用方法名作为消息通道的名称。
     */
    @Input(INPUT) // 定义名为 {@link InputChannel.INPUT} 的通道
    SubscribableChannel input();

}