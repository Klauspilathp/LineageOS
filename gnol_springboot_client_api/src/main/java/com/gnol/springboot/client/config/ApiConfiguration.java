package com.gnol.springboot.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: ApiConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月26日 下午1:06:37
 * @Description: api 项目配置类
 */
@Configuration
public class ApiConfiguration {

    /**
     * gnol 系统自定义属性
     */
    @Bean(name = "gnolProperties")
    @ConditionalOnMissingBean(GnolProperties.class)
    public GnolProperties gnolProperties() {
        return new GnolProperties();
    }

}