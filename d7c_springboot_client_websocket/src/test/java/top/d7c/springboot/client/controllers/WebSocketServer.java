package top.d7c.springboot.client.controllers;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import top.d7c.plugins.core.PageData;

/**
 * @Title: WebSocketServer
 * @Package: top.d7c.springboot.client.controllers
 * @author: 吴佳隆
 * @date: 2021年6月16日 上午10:29:55
 * @Description: 配置 websocket 服务端点
 */
@ServerEndpoint("/webSocket/{userId}")
@Component
public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        logger.info("{} 用户请求建立连接", userId);
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("{} 用户连接关闭", session.getUserPrincipal().getName());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("{} 用户连接出错：{}", session.getUserPrincipal().getName(), throwable.getMessage());
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        PageData pd = JSON.parseObject(message, new TypeReference<PageData>() {

        });
        logger.info("接收到 {} 用户的消息：{}", session.getUserPrincipal().getName(), pd);
    }

    public void sendMessage(Session session, String message) {
        logger.info("给 {} 用户发送消息：{}", session.getUserPrincipal().getName(), message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
