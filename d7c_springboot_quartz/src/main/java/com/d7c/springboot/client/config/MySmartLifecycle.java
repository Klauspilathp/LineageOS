package com.d7c.springboot.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @Title: MySmartLifecycle
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2021年5月8日 下午5:11:14
 * @Description: 当 Spring 容器初始化所有 Bean 之后，会回调该实现类中的 start()。
 */
@Component
public class MySmartLifecycle implements SmartLifecycle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务是否运行正常
     */
    private static boolean IS_RUNNING = false;

    @Override
    public void start() {
        logger.debug("Spring 容器已经初始化完所有 Bean。");
        IS_RUNNING = true;
    }

    /**
     * 1、该方法属于 org.springframework.context.Lifecycle 接口的方法，只有非 org.springframework.context.SmartLifecycle 的子类才会执行该方法；
     * 2、该方法只对实现 org.springframework.context.Lifecycle 接口的类起作用，对实现 org.springframework.context.SmartLifecycle 接口的类无效；
     * 3、实现 org.springframework.context.SmartLifecycle 接口的类专有 stop(Runnable callback)。
     */
    @Override
    public void stop() {
        IS_RUNNING = false;
    }

    /**
     * 当该方法返回 false 时才会执行 start()；
     * 当该方法返回 true 时才能去执行 stop()。
     */
    @Override
    public boolean isRunning() {
        return IS_RUNNING;
    }

}
