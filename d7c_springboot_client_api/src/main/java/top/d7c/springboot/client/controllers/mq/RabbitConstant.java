package top.d7c.springboot.client.controllers.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: RabbitConstant
 * @Package: top.d7c.springboot.client.controllers.mq
 * @author: 吴佳隆
 * @date: 2021年1月6日 下午3:23:21
 * @Description: 定义一些关于 rabbit 消息队列的常量
 */
public interface RabbitConstant {
    static final Logger logger = LoggerFactory.getLogger(RabbitConstant.class);
    // ------- 点对点模式
    /**
     * 点对点（单播）交换机名称，默认的交换机模式，是在创建消息队列的时候指定一个 routingKey，当发送者发送消息的时候指定 routingKey，那么该消息会发送到该队列中。
     */
    String DIRECT = "directExchange";
    // routingKey 名称
    String DIRECT_ROUTINGKEY = "directRoutingKey";
    // 队列名称
    String DIRECT_QUEUE = "directQueue";
    // ------- 发布订阅模式
    /**
     * 发布订阅（多播）交换机名称，将消息发送到与交换机名称匹配的指定队列中（消息进入交换机时就匹配，匹配完成后进入固定队列）。
     * 交换机名称通配符规则：
     *    # 表示零到多个单词；
     *    * 表示一个单词。
     */
    String TOPIC = "topic";
    // routingKey 名称
    String TOPIC_ROUTINGKEY_0 = "topic.routingKey";
    String TOPIC_ROUTINGKEY_1 = "topic.#";
    // 队列名称
    String TOPIC_QUEUE_0 = "topicQueue0";
    String TOPIC_QUEUE_1 = "topicQueue1";
    // ------- 广播模式
    /**
     * 广播（多播）交换机名称，会把消息发送给绑定它的全部队列，即使设置了 routingKey 也会被忽略。
     */
    String FANOUT = "fanoutExchange";
    // 队列名称
    String FANOUT_QUEUE_0 = "fanoutQueue0";
    String FANOUT_QUEUE_1 = "fanoutQueue1";
    String FANOUT_QUEUE_2 = "fanoutQueue2";
    String FANOUT_QUEUE_3 = "fanoutQueue3";
    // ------- 业务队列，就是简单的点对点消息
    String DIRECT_BUS = "directBusExchange";
    // routingKey 名称
    String DIRECT_BUS_ROUTINGKEY = "directBusRoutingKey";
    // 队列名称
    String DIRECT_BUS_QUEUE = "directBusQueue";
    // ------- 死信队列
    String DIRECT_DELAY = "directDelayExchange";
    // routingKey 名称
    String DIRECT_DELAY_ROUTINGKEY = "directDelayRoutingKey";
    // 队列名称
    String DIRECT_DELAY_QUEUE = "directDelayQueue";
    // --- 消息确认机制
    String DIRECT_CONFIRM = "directConfirmExchange";
    // routingKey 名称
    String DIRECT_CONFIRM_ROUTINGKEY = "directConfirmRoutingKey";
    // 队列名称
    String DIRECT_CONFIRM_QUEUE = "directConfirmQueue";

}