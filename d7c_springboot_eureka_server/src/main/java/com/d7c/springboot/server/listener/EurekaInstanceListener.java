package com.d7c.springboot.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

/**
 * @Title: EurekaInstanceListener
 * @Package: com.d7c.springboot.server.listener
 * @author: 吴佳隆
 * @date: 2021年1月7日 下午6:14:33
 * @Description: eureka 实例服务上下线监听。
 */
@Component
public class EurekaInstanceListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(EurekaInstanceListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 服务挂掉事件
        if (event instanceof EurekaInstanceCanceledEvent) {
            EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent = (EurekaInstanceCanceledEvent) event;
            // 获取 Eureka 实例中的节点信息
            PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext()
                    .getRegistry();
            // 遍历获取已注册节点中与当前失效节点 ID 一致的节点信息
            registry.getApplications().getRegisteredApplications().forEach((registeredApplication) -> {
                registeredApplication.getInstances().forEach((instance) -> {
                    if (instance.getInstanceId().equals(eurekaInstanceCanceledEvent.getServerId())) {
                        logger.error("[{}] 服务宕机啦！", instance.getAppName());
                    }
                });
            });

        }
        if (event instanceof EurekaInstanceRegisteredEvent) {
            EurekaInstanceRegisteredEvent eurekaInstanceRegisteredEvent = (EurekaInstanceRegisteredEvent) event;
            logger.debug("[{}] 服务注册成功啦！", eurekaInstanceRegisteredEvent.getInstanceInfo().getAppName());
        }
        if (event instanceof EurekaInstanceRenewedEvent) {
            EurekaInstanceRenewedEvent eurekaInstanceRenewedEvent = (EurekaInstanceRenewedEvent) event;
            logger.debug("[{}] 服务心跳检测！", eurekaInstanceRenewedEvent.getInstanceInfo().getAppName());
        }
        if (event instanceof EurekaRegistryAvailableEvent) {
            logger.debug("当前服务正常！");
        }
    }

}