package com.gnol.springboot.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Title: WebSecurityConfigurer
 * @Package: com.gnol.springboot.server.config
 * @author: 吴佳隆
 * @date: 2020年6月18日 下午3:47:41
 * @Description: eureka 安全认证
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 关闭 csrf
        super.configure(http); // 开启认证
    }

}