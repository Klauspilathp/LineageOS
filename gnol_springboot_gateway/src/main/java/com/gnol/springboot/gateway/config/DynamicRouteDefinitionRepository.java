package com.gnol.springboot.gateway.config;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Title: DynamicRouteDefinitionRepository
 * @Package: com.gnol.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:13:10
 * @Description: 动态路由配置
 */

public class DynamicRouteDefinitionRepository implements RouteDefinitionRepository {

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        // TODO Auto-generated method stub
        return null;
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