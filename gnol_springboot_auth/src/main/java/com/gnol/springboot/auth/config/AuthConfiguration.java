package com.gnol.springboot.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: AuthConfiguration
 * @Package: com.gnol.springboot.auth.config
 * @author: 吴佳隆
 * @date: 2020年7月20日 上午9:44:31
 * @Description: 认证项目配置类
 */
@Configuration
public class AuthConfiguration {

    /**
     * gnol 系统自定义属性
     */
    @Bean(name = "gnolProperties")
    @ConditionalOnMissingBean(GnolProperties.class)
    public GnolProperties gnolProperties() {
        return new GnolProperties();
    }

}