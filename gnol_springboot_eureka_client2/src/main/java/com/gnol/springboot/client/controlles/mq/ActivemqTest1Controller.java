package com.gnol.springboot.client.controlles.mq;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.gnol.activemq.spring.boot.autoconfigure.ActivemqMessage;
import com.gnol.activemq.spring.boot.autoconfigure.SendMessageService;
import com.gnol.springboot.client.controlles.test.Test1Controller;

/**
 * @Title: ActivemqTest1Controller
 * @Package: com.gnol.springboot.client.controlles.mq
 * @author: 吴佳隆
 * @date: 2020年6月30日 下午5:49:22
 * @Description: activemq 点对点模式测试
 */
@Component
public class ActivemqTest1Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test1Controller.class);
    @Resource(name = "sendMessageServiceImpl")
    private SendMessageService sendMessageService;

    public ActivemqTest1Controller() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sendMsg();
            }
        }, 30000, // 任务创建 0.5 分钟后开始执行第一次
                10000); // 每隔 10 秒执行一次
    }

    @JmsListener(destination = "activemq.queue.test1", containerFactory = "queueListener")
    public void receiveQueue(String text) {
        logger.info("ActivemqTest1Controller.receiveQueue activemq.queue.test1 text：{}", text);
    }

    public void sendMsg() {
        for (int i = 0; i < 10; i++) {
            sendMessageService.sendQueueMsg(new ActivemqMessage("activemq.queue.test1", UUID.randomUUID().toString()));
            logger.info("ActivemqTest1Controller.sendMsg activemq.queue.test1");
        }
    }

}