package com.gnol.springboot.client.services.mq.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.tools.date.DateUtil;
import com.gnol.plugins.tools.idfactory.IdFactory;
import com.gnol.springboot.client.services.mq.OutputChannel;

/**
 * @Title: MQProducerServiceImpl
 * @Package: com.gnol.springboot.client.services.mq.impl
 * @author: 吴佳隆
 * @date: 2021年1月5日 上午10:11:38
 * @Description: MQ 消息生产者实现，消费者参见 /gnol_springboot_webflux/src/main/java/com/gnol/springboot/client/services/test/。
 * https://github.com/spring-cloud/spring-cloud-stream/tree/2.1.x
 */
@Service(value = "mQProducerServiceImpl")
@EnableBinding(value = {OutputChannel.class})
public class MQProducerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MQProducerServiceImpl.class);
    /*@Autowired
    private OutputChannel output;*/
    @Autowired
    // @Qualifier(OutputChannel.OUTPUT)
    @Output(OutputChannel.OUTPUT)
    private MessageChannel output;

    /**
     * 调用控制类接口向 {@link OutputChannel.OUTPUT} 通道发送消息。
     */
    public void test0(PageData pd) {
        boolean status =
                // output.output().send(MessageBuilder.withPayload(pd.toString()).setHeader("content-type", "text/html").build());
                output.send(MessageBuilder.withPayload(pd.toString()).setHeader("content-type", "text/html").build());
        logger.info("test0 send to {}(status:{}) message [{}].", OutputChannel.OUTPUT, status, pd.toString());

    }

    /**
     * 向 {@link OutputChannel.OUTPUT} 通道发送消息。默认每秒向消息通道发送 1 条消息。
     * @Poller(fixedRate = "4000", maxMessagesPerPoll = "3") 以固定速率每 4000 ms 向消息通道发送 3 条消息。
     * @Poller(fixedDelay = "5000", maxMessagesPerPoll = "2") 延迟 5000 ms 向消息通道每 5000 ms 发送 2 条消息。
     */
    @Bean // 加上此注解后通道被容器管理，只能看到一条打印日志，但仍是以设置的频率向 MQ 发送消息，不影响效果。
    @InboundChannelAdapter(value = OutputChannel.OUTPUT, poller = @Poller(fixedRate = "4000", maxMessagesPerPoll = "3"))
    public MessageSource<PageData> test1() {
        PageData pd = PageData.build();
        pd.put("id", IdFactory.nextStr());
        pd.put("name", "吴佳隆");
        pd.put("age", 18);
        pd.put("method", "MQProducerServiceImpl.test1");
        pd.put("to", OutputChannel.OUTPUT);
        pd.put("addTime", DateUtil.getDateSecond());
        logger.info("Send to {} message [{}].", OutputChannel.OUTPUT, pd.toString());

        Map<String, Object> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        return () -> new GenericMessage<>(pd, headers);
    }

}