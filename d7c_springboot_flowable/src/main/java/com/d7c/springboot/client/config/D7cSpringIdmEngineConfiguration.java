package com.d7c.springboot.client.config;

import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.idm.spring.authentication.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.d7c.oauth2.spring.boot.SHA1PasswordEncoder;

/**
 * @Title: D7cSpringIdmEngineConfiguration
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2021年4月28日 上午11:25:19
 * @Description: idm 配置类
 */
@Configuration
public class D7cSpringIdmEngineConfiguration extends SpringIdmEngineConfiguration {

    @Bean("sha1PasswordEncoder")
    @Primary
    public PasswordEncoder sha1PasswordEncoder() {
        return new SHA1PasswordEncoder(2);
    }

    @Bean
    public SpringEncoder d7cSpringEncoder() {
        return new SpringEncoder(sha1PasswordEncoder());
    }

    @Override
    public IdmEngineConfiguration setPasswordEncoder(org.flowable.idm.api.PasswordEncoder passwordEncoder) {
        return super.setPasswordEncoder(d7cSpringEncoder());
    }

}
