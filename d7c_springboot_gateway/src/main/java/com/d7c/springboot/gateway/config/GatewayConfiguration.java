package com.d7c.springboot.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: GatewayConfiguration
 * @Package: com.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午7:15:37
 * @Description: gateway 项目自动配置
 */
@Configuration
public class GatewayConfiguration {

    /**
     * d7c 系统自定义属性
     */
    @Bean(name = "d7cProperties")
    @ConditionalOnMissingBean(D7cProperties.class)
    public D7cProperties d7cProperties() {
        return new D7cProperties();
    }

}