package top.d7c.springboot.client.controllers.mq;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import top.d7c.activemq.springboot.autoconfigure.ActivemqMessage;
import top.d7c.activemq.springboot.autoconfigure.SendMessageService;

/**
 * @Title: ActivemqTest1Controller
 * @Package: top.d7c.springboot.client.controllers.mq
 * @author: 吴佳隆
 * @date: 2020年6月30日 下午5:49:22
 * @Description: activemq 点对点模式测试
 */
@Component
public class ActivemqTest1Controller {
    private static final Logger logger = LoggerFactory.getLogger(ActivemqTest1Controller.class);
    @Resource(name = "sendMessageServiceImpl")
    private SendMessageService sendMessageService;

    @JmsListener(destination = "activemq.queue.test1", containerFactory = "queueListener")
    public void receiveQueue(String text) {
        logger.info("ActivemqTest1Controller.receiveQueue activemq.queue.test1 text：{}", text);
    }

    @Scheduled(initialDelay = 20000, fixedDelay = 10000)
    public void sendMsg() {
        for (int i = 0; i < 10; i++) {
            sendMessageService.sendQueueMsg(new ActivemqMessage("activemq.queue.test1", UUID.randomUUID().toString()));
            logger.info("ActivemqTest1Controller.sendMsg activemq.queue.test1");
        }
    }

}