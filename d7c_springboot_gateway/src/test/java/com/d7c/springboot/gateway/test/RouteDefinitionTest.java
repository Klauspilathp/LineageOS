package com.d7c.springboot.gateway.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import com.alibaba.fastjson.JSON;

/**
 * @Title: RouteDefinitionTest
 * @Package: com.d7c.springboot.gateway.test
 * @author: 吴佳隆
 * @date: 2021年5月18日 上午12:26:40
 * @Description: TODO
 */
public class RouteDefinitionTest {

    @Test
    public void test1() {
        List<RouteDefinition> definitions = new ArrayList<RouteDefinition>();

        RouteDefinition routeDefinition0 = new RouteDefinition();
        routeDefinition0.setId("route1");
        routeDefinition0.setOrder(0);
        routeDefinition0.setUri(URI.create("lb://d7c-springboot-config"));
        List<PredicateDefinition> predicates0 = new ArrayList<>();
        PredicateDefinition pd00 = new PredicateDefinition();
        pd00.setName("Path");
        Map<String, String> pdp00 = new HashMap<>(8);
        pdp00.put("pattern", "/web/**");
        pd00.setArgs(pdp00);
        predicates0.add(pd00);
        routeDefinition0.setPredicates(predicates0);
        List<FilterDefinition> filters0 = new ArrayList<>();
        FilterDefinition fd00 = new FilterDefinition();
        fd00.setName("AddRequestHeader");
        Map<String, String> fdp00 = new HashMap<>(8);
        fdp00.put("_genkey_0", "header");
        fdp00.put("_genkey_1", "addHeader");
        fd00.setArgs(fdp00);
        filters0.add(fd00);
        routeDefinition0.setFilters(filters0);
        definitions.add(routeDefinition0);

        RouteDefinition routeDefinition1 = new RouteDefinition();
        routeDefinition1.setId("route2");
        routeDefinition1.setOrder(1);
        routeDefinition1.setUri(URI.create("lb://d7c-springboot-oauth2"));
        List<PredicateDefinition> predicates1 = new ArrayList<>();
        PredicateDefinition pd10 = new PredicateDefinition();
        pd10.setName("Path");
        Map<String, String> pdp10 = new HashMap<>(8);
        pdp10.put("pattern", "/oauth2/**");
        pd10.setArgs(pdp10);
        predicates1.add(pd10);
        routeDefinition1.setPredicates(predicates1);
        List<FilterDefinition> filters1 = new ArrayList<>();
        FilterDefinition fd10 = new FilterDefinition();
        fd10.setName("AddRequestHeader");
        Map<String, String> fdp10 = new HashMap<>(8);
        fdp10.put("_genkey_0", "header");
        fdp10.put("_genkey_1", "addHeader");
        fd10.setArgs(fdp10);
        filters1.add(fd10);
        routeDefinition1.setFilters(filters1);
        definitions.add(routeDefinition1);

        /**
         * [{"filters":[{"args":{"_genkey_0":"header","_genkey_1":"addHeader"},"name":"AddRequestHeader"}],
         * "id":"route1","order":0,"predicates":[{"args":{"pattern":"/web/**"},"name":"Path"}],
         * "uri":"lb://d7c-springboot-config"},
         * {"filters":[{"args":{"_genkey_0":"header","_genkey_1":"addHeader"},"name":"AddRequestHeader"}],
         * "id":"route2","order":1,"predicates":[{"args":{"pattern":"/oauth2/**"},"name":"Path"}],
         * "uri":"lb://d7c-springboot-oauth2"}]
         */
        String definitionsStr = JSON.toJSONString(definitions);
        System.out.println(definitionsStr);

        List<RouteDefinition> routeDefinitionList = JSON.parseArray(definitionsStr, RouteDefinition.class);
        System.out.println(routeDefinitionList);

    }

}
