package com.gnol.springboot.client.services.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Title: OrderLogChannel
 * @Package: com.gnol.springboot.client.services.mq
 * @author: 吴佳隆
 * @date: 2021年1月6日 上午10:21:43
 * @Description: 订单日志消息通道，参考 {@link org.springframework.cloud.stream.messaging.Processor} 写。
 */
public interface OrderLogChannel {
    /**
     * 消息输出（生产）通道名称。
     */
    public static final String OUTPUT = "outputOrderLog";
    /**
     * 消息输入（消费）通道名称。
     */
    public static final String INPUT = "inputOrderLog";

    /**
     * {@link MessageChannel} 该接口定义了向消息通道发送消息的方法。
     * <p/>
     * 如果使用 {@link @Output} 注解而没有指定具体的 value 名称，将默认使用方法名作为消息通道的名称。
     */
    @Output(OUTPUT) // 定义名为 {@link OrderLogChannel.OUTPUT} 的通道
    MessageChannel outputOrderLog();

    /**
     * {@link SubscribableChannel} 该接口定义了从消息通道接收消息的方法。
     * <p/>
     * 如果使用 {@link @Input} 注解而没有指定具体的 value 名称，将默认使用方法名作为消息通道的名称。
     */
    @Input(INPUT) // 定义名为 {@link OrderLogChannel.INPUT} 的通道
    SubscribableChannel inputOrderLog();

}