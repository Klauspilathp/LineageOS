package com.gnol.springboot.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: Oauth2Configuration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月20日 上午9:44:31
 * @Description: 资源授权服务器配置类
 */
@Configuration
public class Oauth2Configuration {

    /**
     * gnol 系统自定义属性
     */
    @Bean(name = "gnolProperties")
    @ConditionalOnMissingBean(GnolProperties.class)
    public GnolProperties gnolProperties() {
        return new GnolProperties();
    }

}