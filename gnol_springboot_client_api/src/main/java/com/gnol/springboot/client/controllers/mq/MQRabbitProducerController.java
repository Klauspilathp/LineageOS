package com.gnol.springboot.client.controllers.mq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.tools.date.DateUtil;

/**
 * @Title: MQRabbitProducerController
 * @Package: com.gnol.springboot.client.controllers.mq
 * @author: 吴佳隆
 * @date: 2021年1月6日 下午2:53:56
 * @Description: rabbit RabbitTemplate 生产消息测试
 */
@RestController
@RequestMapping(value = "/mq/rabbit_producer")
public class MQRabbitProducerController implements RabbitConstant {
    /**
     * 消息发送过程：
     *    当生产者发送消息时，首先会根据指定的 exchange 交换机找到所属的多个队列，而队列绑定的 routingKey 路由键名称不同，消息会根据路由键的名称发送到对应的队列。
     * <p/>
     * 消息消费过程：
     *    消息消费者绑定指定名称的消息队列，从指定的消息队列消费消息。
     * <p/>
     * eg.
     * 将 Java 对象转换为 Amqp {@link Message} 发送到具有特定路由键的 exchange。
     * org.springframework.amqp.rabbit.core.RabbitTemplate.convertAndSend(
     *  String exchange, // 交换机名称
     *  String routingKey, // 路由键名称
     *  Object message // Java 对象的消息
     *  )
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // ------- 点对点模式 ------- start
    // 创建交换机
    @Bean("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT);
    }

    // 创建队列
    @Bean("directQueue")
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE);
    }

    // 把队列绑定到交换机
    @Bean("bindingQueueToDirectExchange")
    public Binding bindingQueueToDirectExchange(@Qualifier("directQueue") Queue queue,
            @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_ROUTINGKEY);
    }

    // 向对列发送一个点对点消息
    @GetMapping(value = "/sendDirectMsgTest")
    public PageResult sendDirectMsgTest() {
        rabbitTemplate.convertAndSend(DIRECT, DIRECT_ROUTINGKEY, "我是一个简单消息，发送时间是" + DateUtil.getDateSecond());
        return PageResult.ok();
    }

    // 从队列获取个点对点消息
    @GetMapping(value = "/getDirectMsgTest")
    public PageResult getDirectMsgTest() {
        Object msg = rabbitTemplate.receiveAndConvert(DIRECT_QUEUE);
        return PageResult.ok(msg);
    }
    // ------- 点对点模式 ------- end

    // ------- 发布订阅模式 ------- start
    // 创建交换机
    @Bean("topicExchange")
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC);
    }

    // 创建第一个队列
    @Bean("topicQueue0")
    public Queue topicQueue0() {
        return new Queue(TOPIC_QUEUE_0);
    }

    // 创建第二个队列
    @Bean("topicQueue1")
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE_1);
    }

    // 把队列绑定到交换机
    @Bean("bindingQueue0ToTopicExchange")
    public Binding bindingQueue0ToTopicExchange(@Qualifier("topicQueue0") Queue queue,
            @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TOPIC_ROUTINGKEY_0);
    }

    @Bean("bindingQueue1ToTopicExchange")
    public Binding bindingQueue1ToTopicExchange(@Qualifier("topicQueue1") Queue queue,
            @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TOPIC_ROUTINGKEY_1);
    }

    // 向对列发送一个发布订阅消息
    @GetMapping(value = "/sendTopicMsgTest")
    public PageResult sendTopicMsgTest() {
        rabbitTemplate.convertAndSend(TOPIC, TOPIC_ROUTINGKEY_0,
                "我向{" + TOPIC_ROUTINGKEY_0 + "}发送了一个消息，发送时间是" + DateUtil.getDateSecond());
        rabbitTemplate.convertAndSend(TOPIC, "topic.a",
                "我向{" + "topic.a" + "}发送了一个消息，发送时间是" + DateUtil.getDateSecond());
        rabbitTemplate.convertAndSend(TOPIC, "topic.bcd",
                "我向{" + "topic.bcd" + "}发送了一个消息，发送时间是" + DateUtil.getDateSecond());
        return PageResult.ok();
    }
    // ------- 发布订阅模式 ------- end

    // ------- 广播模式 ------- start
    @Bean("fanoutQueue0")
    public Queue fanoutQueue0() {
        return new Queue(FANOUT_QUEUE_0);
    }

    @Bean("fanoutQueue1")
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE_1);
    }

    @Bean("fanoutQueue2")
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE_2);
    }

    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT);
    }

    @Bean("bindingQueue0ToFanoutExchange")
    Binding bindingQueue0ToFanoutExchange(@Qualifier("fanoutQueue0") Queue fanoutQueue0,
            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue0).to(fanoutExchange);
    }

    @Bean("bindingQueue1ToFanoutExchange")
    Binding bindingQueue1ToFanoutExchange(@Qualifier("fanoutQueue1") Queue fanoutQueue1,
            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean("bindingQueue2ToFanoutExchange")
    Binding bindingQueue2ToFanoutExchange(@Qualifier("fanoutQueue2") Queue fanoutQueue2,
            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    // 向对列发送一个广播消息
    @GetMapping(value = "/sendFanoutMsgTest")
    public PageResult sendFanoutMsgTest() {
        rabbitTemplate.convertAndSend(FANOUT, null, "我发送了一个广播消息，发送时间是" + DateUtil.getDateSecond());
        return PageResult.ok();
    }
    // ------- 广播模式 ------- end

    /**
     * 管理组件
     */
    @Autowired
    private AmqpAdmin amqpAdmin;

    // 创建交换机与队列的绑定关系
    @GetMapping(value = "/createExchange")
    public PageResult createExchange() {
        // 创建队列
        amqpAdmin.declareQueue(new Queue(FANOUT_QUEUE_3, true // 是否持久化
        ));
        // amqpAdmin.declareExchange(new FanoutExchange(FANOUT));
        // 创建绑定关系
        amqpAdmin.declareBinding(new Binding(FANOUT_QUEUE_3, Binding.DestinationType.QUEUE, FANOUT, "", null));
        return PageResult.ok();
    }

    // ------- 业务队列，就是简单的点对点消息 ------- start
    // 创建交换机
    @Bean("directBusExchange")
    public DirectExchange directBusExchange() {
        return new DirectExchange(DIRECT_BUS);
    }

    // 创建队列并绑定死信队列
    @Bean("directBusQueue")
    public Queue directBusQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的 DLX 名称
        params.put("x-dead-letter-exchange", DIRECT_DELAY);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key
        params.put("x-dead-letter-routing-key", DIRECT_DELAY_ROUTINGKEY);
        // 设置队列中消息的过期时间，单位毫秒 x-message-ttl
        // params.put("x-expires", 5 * 1000);
        return new Queue(DIRECT_BUS_QUEUE, true, false, false, params);

    }

    // 把队列绑定到交换机
    @Bean("bindingBusQueueToDirectExchange")
    public Binding bindingBusQueueToDirectExchange(@Qualifier("directBusQueue") Queue queue,
            @Qualifier("directBusExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_BUS_ROUTINGKEY);
    }
    // ------- 业务队列，就是简单的点对点消息 ------- end

    // ------- 死信队列 ------- start
    // 创建交换机
    @Bean("directDelayExchange")
    public DirectExchange directDelayExchange() {
        return new DirectExchange(DIRECT_DELAY);
    }

    // 创建死信队列
    @Bean("directDelayQueue")
    public Queue directDelayQueue() {
        return new Queue(DIRECT_DELAY_QUEUE);
    }

    // 把死信队列绑定到交换机
    @Bean("bindingDelayQueueToDirectExchange")
    public Binding bindingDelayQueueToDirectExchange(@Qualifier("directDelayQueue") Queue queue,
            @Qualifier("directDelayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_DELAY_ROUTINGKEY);
    }

    // 创建死信队列
    @GetMapping(value = "/createDirectDelayQueue")
    public PageResult createDirectDelayQueue() {
        // 创建队列
        amqpAdmin.declareQueue(new Queue(DIRECT_DELAY_QUEUE, true // 是否持久化
        ));
        amqpAdmin.declareExchange(new DirectExchange(DIRECT_DELAY));
        // 创建绑定关系
        amqpAdmin.declareBinding(new Binding(DIRECT_DELAY_QUEUE, Binding.DestinationType.QUEUE, DIRECT_DELAY,
                DIRECT_DELAY_ROUTINGKEY, null));
        return PageResult.ok();
    }

    // 向业务对列发送一个点对点消息
    @GetMapping(value = "/sendBusMsgTest")
    public PageResult sendBusMsgTest() {
        // 设置每个消息都返回一个确认消息
        rabbitTemplate.setMandatory(true);
        // 消息确认机制
        rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
            logger.info("消息确认 [{}]！", ack ? "成功" : "失败");
        });
        // 消息发送失败机制
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            logger.info("发送失败的消息 [{}]", new String(message.getBody()));
        });
        rabbitTemplate.convertAndSend(DIRECT_BUS, DIRECT_BUS_ROUTINGKEY,
                "我是一个业务消息，我将被转发至死信队列，发送时间是" + DateUtil.getDateSecond());
        return PageResult.ok();
    }
    // ------- 死信队列 ------- end

}