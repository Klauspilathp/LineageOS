package top.d7c.springboot.gateway.test;

/**
 * @Title: DynamicRouteTest
 * @Package: top.d7c.springboot.gateway.test
 * @author: 吴佳隆
 * @date: 2021年5月18日 上午12:44:35
 * @Description: TODO
 */
public class DynamicRouteTest {
    /**
     * 测试步骤：
     * 1、其他服务预热路由定义数据，向 Redis 中放入路由规则数据；
     * 2、如果事先没有预热，则启动该服务后会向 top.d7c.springboot.gateway.config.GatewayConfiguration.GATEWAY_ROUTES_DIRECT_QUEUE
     * 点对点队列中发送一条消息，其他服务监听到该消息后需将路由规则一条一条发送到 top.d7c.springboot.gateway.config.GatewayConfiguration.GATEWAY_ROUTES_TOPIC_QUEUE
     * 消息队列，所有网关服务都会监听 top.d7c.springboot.gateway.config.GatewayConfiguration.GATEWAY_ROUTES_TOPIC_QUEUE 发布订阅消息队列；
     * 3、需要添加或删除路由规则时，直接向 top.d7c.springboot.gateway.config.GatewayConfiguration.GATEWAY_ROUTES_TOPIC_QUEUE
     * 消息队列发送一条发布订阅消息即可；
     * 4、调用 http://127.0.0.1:8081/actuator/gateway/routes 接口随时查询所有路由规则。
     * 
     * 注意：如果配置文件中存在路由定义，那么这些路由定义将不能被操作，通过 /actuator/gateway/routes 端点可以查询到。
     */

}
