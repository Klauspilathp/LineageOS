package top.d7c.springboot.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Title: WebSocketConfiguration
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月14日 下午4:25:19
 * @Description: websocket 配置
 */
@Configuration
@EnableWebSocketMessageBroker // 开启 STOMP 协议来传输基于代理（message broker）消息
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册协议节点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个点对点节点和一个广播订阅节点，并指定为 SockJS 协议。
        registry.addEndpoint("/endpoint-queue", "/endpoint-topic").withSockJS();
    }

    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置点对点节点和广播订阅节点的消息代理
        registry.enableSimpleBroker("/queue", "/topic");
    }

}