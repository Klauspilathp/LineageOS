package com.d7c.springboot.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.d7c.plugins.core.StringUtil;

import reactor.core.publisher.Mono;

/**
 * @Title: RouteAmqpConsumer
 * @Package: com.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:37:55
 * @Description: 路由规则消费者去监听路由队列的变化并发布事件
 */
@Component
@RabbitListener(queues = GatewayConfiguration.GATEWAY_ROUTES_TOPIC_QUEUE) // 监听路由规则队列
public class RouteAmqpConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RouteAmqpConsumer.class);
    /**
     * 监听到队列改变时发布事件
     */
    @Autowired
    private ApplicationEventPublisher publisher;
    /**
     * 动态路由配置
     */
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @RabbitHandler
    public void routeHandler(String msg) {
        GatewayRouteDefinition gatewayRouteDefinition = JSON.parseObject(msg, GatewayRouteDefinition.class);
        if (gatewayRouteDefinition == null) {
            return;
        }
        String operationType = gatewayRouteDefinition.getOperationType();
        if (StringUtil.isBlank(operationType)) {
            return;
        }
        if (operationType.equalsIgnoreCase(GatewayRouteDefinition.OperationType.ADD.name())
                || operationType.equalsIgnoreCase(GatewayRouteDefinition.OperationType.UPDATE.name())) {
            RouteDefinition routeDefinition = gatewayRouteDefinition.parseToRouteDefinition();
            if (routeDefinition != null) {
                try {
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                } catch (Exception e) {
                    logger.error("Error saving routing rule : {}", e.getMessage());
                }
            }
        } else if (operationType.equalsIgnoreCase(GatewayRouteDefinition.OperationType.DELETE.name())) {
            String id = gatewayRouteDefinition.getId();
            if (StringUtil.isNotBlank(id)) {
                try {
                    routeDefinitionWriter.delete(Mono.just(id)).subscribe();
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                } catch (Exception e) {
                    logger.error("Error deleting routing rule : {}", e.getMessage());
                }
            }
        }

    }

}