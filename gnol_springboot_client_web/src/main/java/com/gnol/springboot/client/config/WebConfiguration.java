package com.gnol.springboot.client.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Title: WebConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月4日 下午5:03:59
 * @Description: web 项目配置类
 */
@Configuration
public class WebConfiguration {

    /**
     * 线程池配置
     */
    @Bean(name = "threadPoolTaskExecutor")
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 线程池维护线程的最少数量
        int core = Runtime.getRuntime().availableProcessors();
        threadPoolTaskExecutor.setCorePoolSize(core);
        // 线程池维护线程的最大数量，默认为 Integer.MAX_VALUE
        threadPoolTaskExecutor.setMaxPoolSize(core * 10);
        // 线程池所使用的缓冲队列，一般需要设置值 >= notifyScheduledMainExecutor.maxNum；默认为 Integer.MAX_VALUE
        threadPoolTaskExecutor.setQueueCapacity(20000);
        // 线程池维护线程所允许的空闲时间，默认为 60s
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        /**
         * 线程池对拒绝任务（无线程可用）的处理策略，目前只支持 AbortPolicy、CallerRunsPolicy，默认为后者
         * AbortPolicy：直接抛出 java.util.concurrent.RejectedExecutionException 异常
         * CallerRunsPolicy：主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
         * DiscardOldestPolicy：抛弃旧的任务暂不支持，会导致被丢弃的任务无法再次被执行
         * DiscardPolicy：抛弃当前任务暂不支持，会导致被丢弃的任务无法再次被执行
         */
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolTaskExecutor;
    }

}