package top.d7c.springboot.client.controllers.sys;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @Title: QueueController
 * @Package: top.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月14日 下午4:33:49
 * @Description: websocket 队列消息测试处理器
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@MessageMapping(value = "/sys/queue")
public class QueueController {
    /**
     * 使用 SimpMessagingTemplate 向浏览器发送消息
     */
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @Title: chat
     * @author: 吴佳隆
     * @data: 2020年7月14日 下午6:43:53
     * @Description: 接收消息
     * @param principal 当前登录用户信息
     * @param message   接收消息
     */
    @MessageMapping("/chat")
    public void chat(Principal principal, Map<String, Object> message) {
        // 如果当前登录人是 admin，则将消息发送给 wujialong。反之亦然
        if (principal.getName().equals("admin")) {
            template.convertAndSendToUser("wujialong", "/queue/chat",
                    principal.getName() + "-send:" + message.toString());
        } else {
            template.convertAndSendToUser("admin", "/queue/chat", principal.getName() + "-send:" + message.toString());
        }
    }

}