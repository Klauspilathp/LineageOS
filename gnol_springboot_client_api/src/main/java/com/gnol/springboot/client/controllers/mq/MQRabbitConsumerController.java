package com.gnol.springboot.client.controllers.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Title: MQRabbitConsumerController
 * @Package: com.gnol.springboot.client.controllers.mq
 * @author: 吴佳隆
 * @date: 2021年1月6日 下午2:53:56
 * @Description: rabbit RabbitTemplate 消费消息测试
 */
@Component
public class MQRabbitConsumerController implements RabbitConstant {

    // ------- 点对点模式
    /**
     * 也可以在类上加 @RabbitListener(queues = DIRECT_QUEUE)，然后在方法上加 @RabbitHandler 接收消息。
     */
    @RabbitListener(queues = DIRECT_QUEUE)
    public void getDirectMsgTest(Object msg) {
        Message message = (Message) msg;
        logger.info("getDirectMsgTest received from {} message: [{}].", DIRECT_QUEUE, new String(message.getBody()));
    }

    // ------- 发布订阅模式

    // ------- 广播模式

}