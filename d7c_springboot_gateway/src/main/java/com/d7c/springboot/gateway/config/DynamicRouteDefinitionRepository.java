package com.d7c.springboot.gateway.config;

import static java.util.Collections.synchronizedMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.d7c.plugins.core.StringUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Title: DynamicRouteDefinitionRepository
 * @Package: com.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:13:10
 * @Description: 动态路由配置。
 * 参考：
 *    org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository，
 * 从 org.springframework.cloud.gateway.config.GatewayAutoConfiguration 类中可以看出如果没有
 * org.springframework.cloud.gateway.route.RouteDefinitionRepository 类型的 Bean 则使用
 * org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository 类加载路由规则。
 * 配置信息加载顺序：
 *    1、PropertiesRouteDefinitionLocator --> 配置文件加载初始化 --> CompositeRouteDefinitionLocator；
 *    2、RouteDefinitionRepository --> 存储器中加载初始化 --> CompositeRouteDefinitionLocator；
 *    3、DiscoveryClientRouteDefinitionLocator --> 注册中心加载初始化 --> CompositeRouteDefinitionLocator。
 */
@Repository
public class DynamicRouteDefinitionRepository implements RouteDefinitionRepository {
    private static final Logger logger = LoggerFactory.getLogger(DynamicRouteDefinitionRepository.class);
    /**
     * 路由规则定义容器
     */
    private final Map<String, RouteDefinition> routes = synchronizedMap(new LinkedHashMap<String, RouteDefinition>());
    /**
     * redis 访问对象助手
     */
    @Autowired
    private RedisTemplate<String, Object> template;
    /**
     * amqp 模板
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostConstruct
    public void loadRouteDefinitions() {
        // 从 redis 中获取路由列表
        Object routeDefinitions = template.opsForValue().get("gateway:routes");
        if (StringUtil.isNotBlank(routeDefinitions)) {
            // 将 routeDefinitions 解析成 List<RouteDefinition> 并赋值给 definitions
            List<RouteDefinition> definitions = JSON.parseArray(JSON.toJSONString(routeDefinitions),
                    RouteDefinition.class);
            if (CollectionUtils.isNotEmpty(definitions)) {
                // 将路由定义列表保存到本地缓存，减少查询，提高效率。
                definitions.stream().forEach(route -> {
                    routes.put(route.getId(), route);
                });
                return;
            }
        }
        // 向消息队列发送一条点对点消息，通知加载数据
        GatewayRouteDefinition gatewayRouteDefinition = new GatewayRouteDefinition();
        gatewayRouteDefinition.setOperationType(GatewayRouteDefinition.OperationType.SELECT.name());
        amqpTemplate.convertAndSend(GatewayConfiguration.GATEWAY_ROUTES_DIRECT_EXCHANGE,
                GatewayConfiguration.GATEWAY_ROUTES_DIRECT_ROUTINGKEY, JSON.toJSONString(gatewayRouteDefinition));
        logger.warn("Redis 中不存在路由定义列表，向消息队列中发一条消息通知其他服务从数据库中查询路由定义数据并放入 Redis 中。");
    }

    /**
     * 该方法默认 eureka.client.registry-fetch-interval-seconds 秒刷新一次。
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routes.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (StringUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + id)));
        });
    }

}