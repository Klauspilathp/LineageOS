package com.gnol.springboot.common.configurations.config;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.gnol.plugins.core.context.GnolApplicationObjectSupport;
import com.gnol.springboot.common.actuator.endpoint.CustomEndPoint;

/**
 * @Title: CommonConfiguration
 * @Package: com.gnol.springboot.common.configurations.config
 * @author: 吴佳隆
 * @date: 2020年7月3日 下午2:46:03
 * @Description: 公共自动配置类
 */
@Configuration
public class CommonConfiguration {

    /**
     * 初始化  org.springframework.context.ApplicationContext
     */
    @Bean("gnolApplicationObjectSupport")
    public GnolApplicationObjectSupport gnolApplicationObjectSupport() {
        return new GnolApplicationObjectSupport();
    }

    /**
     * 自定义监控端点。
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public CustomEndPoint customEndPoint(Environment environment) {
        return new CustomEndPoint(environment);
    }

}