package com.gnol.springboot.client.services.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Title: OutputChannel
 * @Package: com.gnol.springboot.client.services.mq
 * @author: 吴佳隆
 * @date: 2021年1月5日 上午10:06:15
 * @Description: 消息输出（生产）通道。可以参考 {@link org.springframework.cloud.stream.messaging.Source} 写。
 * 想同时定义一个消息输入（消费）通道和一个消息输出（生产）通道可以参考 {@link org.springframework.cloud.stream.messaging.Processor} 写。
 */
public interface OutputChannel {
    /**
     * 消息输出（生产）通道名称。
     */
    public static final String OUTPUT = "outputOrder";

    /**
     * {@link MessageChannel} 该接口定义了向消息通道发送消息的方法。
     * <p/>
     * 如果使用 {@link @Output} 注解而没有指定具体的 value 名称，将默认使用方法名作为消息通道的名称。
     */
    @Output(OUTPUT) // 定义名为 {@link OutputChannel.OUTPUT} 的通道
    MessageChannel output();

}