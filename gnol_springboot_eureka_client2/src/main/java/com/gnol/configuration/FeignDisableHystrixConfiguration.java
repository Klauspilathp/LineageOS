package com.gnol.configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Feign;

/**
 * @Title: FeignDisableHystrixConfiguration
 * @Package: com.gnol.configuration
 * @author: 吴佳隆
 * @date: 2020年6月19日 下午6:50:05
 * @Description: 为部分 Feign 禁用 Hystrix 功能，该类不需要被 spring boot 启动类扫描到
 */
@Configuration
public class FeignDisableHystrixConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

}