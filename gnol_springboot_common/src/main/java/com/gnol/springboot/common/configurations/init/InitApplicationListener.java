package com.gnol.springboot.common.configurations.init;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Title: InitApplicationListener
 * @Package: com.gnol.springboot.common.configurations.init
 * @author: 吴佳隆
 * @date: 2020年7月2日 下午2:29:46
 * @Description: 系统初始化监听
 */
@Component
public class InitApplicationListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    @Override
    public int getOrder() {
        // 最后执行
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * bean 初始化时被执行
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 获取一个接口的所有实现 bean event.getApplicationContext().getBeansOfType(Class<T> type)
    }

}