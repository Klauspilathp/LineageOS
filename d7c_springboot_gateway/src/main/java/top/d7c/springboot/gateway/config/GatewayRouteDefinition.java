package top.d7c.springboot.gateway.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import com.alibaba.fastjson.JSONObject;
import top.d7c.plugins.core.StringUtil;

/**
 * @Title: GatewayRouteDefinition
 * @Package: top.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2021年5月18日 下午12:48:59
 * @Description: gateway 路由定义
 */
public class GatewayRouteDefinition {
    /**
     * 路由定义 ID 名称，默认是 org.springframework.util.IdGenerator.generateId().toString()
     */
    private String id;
    /**
     * 路由顺序，越小越优先
     */
    private int order = 0;
    /**
     * uri 字符串
     */
    private String uri;
    /**
     * 路由断言字符串
     */
    private String predicates;
    /**
     * 路由过滤规则字符串
     */
    private String filters;
    /**
     * 操作类型
     */
    private String operationType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public URI parseToUri() {
        return URI.create(this.uri);
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    public List<PredicateDefinition> parseToPredicateDefinition() {
        if (StringUtil.isBlank(this.predicates)) {
            return new ArrayList<>();
        }
        return JSONObject.parseArray(this.predicates, PredicateDefinition.class);
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public List<FilterDefinition> parseToFilterDefinition() {
        if (StringUtil.isBlank(this.filters)) {
            return new ArrayList<>();
        }
        return JSONObject.parseArray(this.filters, FilterDefinition.class);
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public RouteDefinition parseToRouteDefinition() {
        if (StringUtil.isBlank(this.id)) {
            throw new IllegalArgumentException("id cannot be empty!");
        }
        if (StringUtil.isBlank(this.uri)) {
            throw new IllegalArgumentException("uri cannot be empty!");
        }
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(this.id);
        routeDefinition.setOrder(this.order);
        routeDefinition.setUri(this.parseToUri());
        routeDefinition.setPredicates(this.parseToPredicateDefinition());
        routeDefinition.setFilters(this.parseToFilterDefinition());
        return routeDefinition;
    }

    enum OperationType {
        ADD, DELETE, UPDATE, SELECT;
    }

}
