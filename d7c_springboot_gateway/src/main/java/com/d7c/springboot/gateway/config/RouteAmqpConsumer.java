package com.d7c.springboot.gateway.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @Title: RouteAmqpConsumer
 * @Package: com.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:37:55
 * @Description: 路由规则消费者去监听路由队列的变化并发布事件
 */
// @Component
// @RabbitListener(queues = "gateway:route") // 监听路由规则队列
public class RouteAmqpConsumer {
    /**
     * 监听到队列改变时发布事件
     */
    @Autowired
    private ApplicationEventPublisher publisher;

    @RabbitHandler
    public void routeHandler() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }

}