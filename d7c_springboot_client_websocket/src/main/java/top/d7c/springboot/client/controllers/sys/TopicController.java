package top.d7c.springboot.client.controllers.sys;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: TopicController
 * @Package: top.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月14日 下午4:33:49
 * @Description: websocket 订阅消息测试处理器
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@MessageMapping(value = "/sys/topic")
public class TopicController {

    @MessageMapping("/send")
    @SendTo("/topic/topic-server")
    @ResponseBody
    public String send(Map<String, Object> message) {
        return "Welcome, " + message.get("username") + "!";
    }

}