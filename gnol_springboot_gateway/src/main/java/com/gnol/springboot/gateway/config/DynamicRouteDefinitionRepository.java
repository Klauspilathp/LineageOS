package com.gnol.springboot.gateway.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.StringUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Title: DynamicRouteDefinitionRepository
 * @Package: com.gnol.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:13:10
 * @Description: 动态路由配置，org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository
 */
@Repository
public class DynamicRouteDefinitionRepository implements RouteDefinitionRepository {
    private static final Logger logger = LoggerFactory.getLogger(DynamicRouteDefinitionRepository.class);
    /**
     * redis 对 String 操作模板
     */
    @Autowired
    private StringRedisTemplate template;
    /**
     * amqp 模板
     */
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 路由定义
     */
    private static List<RouteDefinition> definitions = new ArrayList<RouteDefinition>();

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        // 从 redis 中获取路由列表
        String routesStr = template.opsForValue().get("gateway:routes");
        if (StringUtil.isNotBlank(routesStr)) {
            // 将 routeStr 解析成 List<RouteDefinition> 并赋值给 definitions
        } else {
            amqpTemplate.convertAndSend("gateway:routes", true);
            logger.debug("向队列中发一条消息通知消费者 redis 中的路由定义不存在，该查询并放入缓存了");
        }
        return Flux.fromIterable(definitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

}