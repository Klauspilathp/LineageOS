package com.gnol.springboot.client.controllers.mq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 * @Title: MQRabbitConsumerController
 * @Package: com.gnol.springboot.client.controllers.mq
 * @author: 吴佳隆
 * @date: 2021年1月6日 下午2:53:56
 * @Description: rabbit RabbitTemplate 消费消息测试
 */
@Component
public class MQRabbitConsumerController implements RabbitConstant {

    // ------- 点对点模式 ------- start
    /**
     * 也可以在类上加 @RabbitListener(queues = DIRECT_QUEUE)，然后在方法上加 @RabbitHandler 接收消息。
     */
    @RabbitListener(queues = DIRECT_QUEUE)
    public void getDirectMsgTest(Object msg) {
        Message message = (Message) msg;
        logger.info("getDirectMsgTest received from {} message: [{}].", DIRECT_QUEUE, new String(message.getBody()));
    }
    // ------- 点对点模式 ------- end

    // ------- 发布订阅模式 ------- start
    @RabbitListener(queues = TOPIC_QUEUE_0)
    public void getTopic0MsgTest(Object msg) {
        Message message = (Message) msg;
        logger.info("getTopic0MsgTest received from {} message: [{}].", TOPIC_QUEUE_0, new String(message.getBody()));
    }

    @RabbitListener(queues = TOPIC_QUEUE_1)
    public void getTopic1MsgTest(Object msg) {
        Message message = (Message) msg;
        logger.info("getTopic1MsgTest received from {} message: [{}].", TOPIC_QUEUE_1, new String(message.getBody()));
    }
    // ------- 发布订阅模式 ------- end

    // ------- 广播模式 ------- start
    @RabbitListener(queues = FANOUT_QUEUE_0)
    public void getFanout0MsgTest(Message msg) {
        logger.info("getFanout0MsgTest received from {} message: [{}].", FANOUT_QUEUE_0, new String(msg.getBody()));
    }

    @RabbitListener(queues = FANOUT_QUEUE_1)
    public void getFanout1MsgTest(Message msg) {
        logger.info("getFanout1MsgTest received from {} message: [{}].", FANOUT_QUEUE_1, new String(msg.getBody()));
    }

    @RabbitListener(queues = FANOUT_QUEUE_2)
    public void getFanout2MsgTest(Message msg) {
        logger.info("getFanout2MsgTest received from {} message: [{}].", FANOUT_QUEUE_2, new String(msg.getBody()));
    }

    // 先用 /mq/rabbit_producer/createExchange 接口创建队列，再放开注解监听！！！
    // @RabbitListener(queues = FANOUT_QUEUE_3)
    public void getFanout3MsgTest(Message msg) {
        logger.info("getFanout3MsgTest received from {} message: [{}].", FANOUT_QUEUE_3, new String(msg.getBody()));
    }
    // ------- 广播模式 ------- end

    // ------- 业务队列，就是简单的点对点消息 ------- start
    @RabbitListener(queues = DIRECT_BUS_QUEUE)
    public void getBusMsgTest(Channel channel, Message msg) {
        logger.info("getBusMsgTest received from {} message: [{}].", DIRECT_BUS_QUEUE, new String(msg.getBody()));
        try {
            // 手动签收
            // channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            // 拒绝消费消息（丢失消息） 给死信队列
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), // 收到的标签
                    false, // 只拒绝提供的交付标签
                    false // 不会重回队列
            );
            logger.info("我拒绝接受它！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DIRECT_BUS_QUEUE 队列拒绝签收消息后，该消息变成死信消息被转发到死信队列消费。
    @RabbitListener(queues = DIRECT_DELAY_QUEUE)
    public void getDelayMsgTest(Message msg) {
        logger.info("getDelayMsgTest received from {} message: [{}].", DIRECT_DELAY_QUEUE, new String(msg.getBody()));
    }
    // ------- 业务队列，就是简单的点对点消息 ------- end

    // --- 消息确认机制 ------- start
    @RabbitListener(queues = DIRECT_CONFIRM_QUEUE
    /*, bindings = @QueueBinding( // 队列和交换机的绑定关系
    value = @Queue(DIRECT_CONFIRM_QUEUE), // 队列名称
    exchange = @Exchange(name = DIRECT_CONFIRM, // 交换机名称
            type = "direct" // 交换机类型
    ))*/
    )
    public void getConfirmMsgTest(Channel channel,
            /*@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,*/
            Message msg) {
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        logger.info("getConfirmMsgTest received from {} message: [{}].", DIRECT_CONFIRM_QUEUE,
                new String(msg.getBody()));
        // 成功确认消息
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
            // 失败确认
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
    // --- 消息确认机制 ------- end

}