package com.gnol.springboot.common.configurations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gnol.plugins.core.context.GnolApplicationObjectSupport;

/**
 * @Title: CommonConfiguration
 * @Package: com.gnol.springboot.common.configurations.config
 * @author: 吴佳隆
 * @date: 2020年7月3日 下午2:46:03
 * @Description: 公共自动配置类
 */
@Configuration
public class CommonConfiguration {

    @Bean
    public GnolApplicationObjectSupport gnolApplicationObjectSupport() {
        return new GnolApplicationObjectSupport();
    }

}