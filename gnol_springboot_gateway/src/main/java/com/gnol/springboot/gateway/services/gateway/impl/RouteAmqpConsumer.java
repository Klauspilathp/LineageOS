package com.gnol.springboot.gateway.services.gateway.impl;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.gnol.springboot.gateway.services.gateway.RouteService;

/**
 * @Title: RouteAmqpConsumer
 * @Package: com.gnol.springboot.gateway.services.gateway.impl
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:37:55
 * @Description: 路由规则消费者去监听路由变化，同时摒弃使用 Controller 去操作路由规则
 */
@Component
@RabbitListener(queues = "route") // 监听路由规则队列
public class RouteAmqpConsumer {
    /**
     * 路由服务实现
     */
    @Resource(name = "routeServiceImpl")
    private RouteService routeService;

    @RabbitHandler
    public void routeHandler() {

    }

}