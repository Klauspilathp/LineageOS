package com.d7c.springboot.gateway.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: GatewayConfiguration
 * @Package: com.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午7:15:37
 * @Description: gateway 项目自动配置
 */
@Configuration
public class GatewayConfiguration {
    /**
     * gateway 点对点消息路由交换机名称、routingKey 名称和队列名称
     */
    public static final String GATEWAY_ROUTES_DIRECT_EXCHANGE = "gatewayRoutesDirectExchange";
    public static final String GATEWAY_ROUTES_DIRECT_ROUTINGKEY = "gatewayRoutesDirectRoutingKey";
    public static final String GATEWAY_ROUTES_DIRECT_QUEUE = "gatewayRoutesDirectQueue";
    /**
     * gateway 发布订阅消息路由交换机名称、routingKey 名称和队列名称
     */
    public static final String GATEWAY_ROUTES_TOPIC_EXCHANGE = "gatewayRoutesTopicExchange";
    public static final String GATEWAY_ROUTES_TOPIC_ROUTINGKEY = "gatewayRoutesTopicRoutingKey";
    public static final String GATEWAY_ROUTES_TOPIC_QUEUE = "gatewayRoutesTopicQueue";

    /**
     * d7c 系统自定义属性
     */
    @Bean(name = "d7cProperties")
    @ConditionalOnMissingBean(D7cProperties.class)
    public D7cProperties d7cProperties() {
        return new D7cProperties();
    }

    // -------------------------------- 点对点消息
    /**
     * 不创建 gatewayRoutesDirectExchange 和 bindingGatewayRoutesQueueToDirectExchange Bean 时
     * GATEWAY_ROUTES_DIRECT_QUEUE 队列会绑定到默认交换机上。
     */
    // 创建交换机
    @Bean("gatewayRoutesDirectExchange")
    @ConditionalOnMissingBean(name = "gatewayRoutesDirectExchange")
    public DirectExchange gatewayRoutesDirectExchange() {
        return new DirectExchange(GATEWAY_ROUTES_DIRECT_EXCHANGE);
    }

    // 创建队列
    @Bean("gatewayRoutesDirectQueue")
    @ConditionalOnMissingBean(name = "gatewayRoutesDirectQueue")
    public Queue gatewayRoutesDirectQueue() {
        return new Queue(GATEWAY_ROUTES_DIRECT_QUEUE);
    }

    // 把队列绑定到交换机
    @Bean("bindingGatewayRoutesQueueToDirectExchange")
    @ConditionalOnMissingBean(name = "bindingGatewayRoutesQueueToDirectExchange")
    public Binding bindingGatewayRoutesQueueToDirectExchange(@Qualifier("gatewayRoutesDirectQueue") Queue queue,
            @Qualifier("gatewayRoutesDirectExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(GATEWAY_ROUTES_DIRECT_ROUTINGKEY);
    }

    // -------------------------------- 发布订阅消息
    // 创建交换机
    @Bean("gatewayRoutesTopicExchange")
    public TopicExchange gatewayRoutesTopicExchange() {
        return new TopicExchange(GATEWAY_ROUTES_TOPIC_EXCHANGE);
    }

    // 创建队列
    @Bean("gatewayRoutesTopicQueue")
    public Queue gatewayRoutesTopicQueue() {
        return new Queue(GATEWAY_ROUTES_TOPIC_QUEUE);
    }

    // 把队列绑定到交换机
    @Bean("bindingGatewayRoutesQueueToTopicExchange")
    public Binding bindingGatewayRoutesQueueToTopicExchange(@Qualifier("gatewayRoutesTopicQueue") Queue queue,
            @Qualifier("gatewayRoutesTopicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(GATEWAY_ROUTES_TOPIC_ROUTINGKEY);
    }

}