package com.d7c.springboot.client.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @Title: WebSecurityConfiguration
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午3:49:51
 * @Description: web security 配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // spring security 默认认证密码必须是加密的，加上 {noop} 表示认证密码是不加密的。
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER").and()
                .withUser("wujialong").password("{noop}000000").roles("USER");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 关闭 csrf 跨站访问拦截
                .headers().frameOptions().disable() // 不允许 iframe 内呈现
                .and().cors() // 支持跨域请求
                .and().authorizeRequests()
                .antMatchers("/eureka/apps/**"/*eureka 心跳相关*/, "/actuator", "/actuator/**"/*监控相关*/,
                        "/static/**"/*静态资源*/, "/favicon.ico")
                .permitAll() // 免授权请求配置
                .anyRequest().authenticated() // 其余所有请求都需要授权
                .and().formLogin().loginProcessingUrl("/login") // 配置以 form 表单形式登录参数及请求路径
                .successHandler(new AuthenticationSuccessHandler() { // 登录成功后操作
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
                        logger.debug("{} 用户在 {} 地址登录成功！", authentication.getName(), request.getRemoteAddr());
                        response.sendRedirect("/index");
                    }
                }).and().logout() // 登出授权
                .invalidateHttpSession(true).clearAuthentication(true);
    }

}