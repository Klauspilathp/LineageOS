package com.gnol.springboot.client.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @Title: WebSecurityConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午3:49:51
 * @Description: web security 配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( // 开启 security 注解
        prePostEnabled = false, // 基于表达式进行方法级别的访问控制
        securedEnabled = false, // security 内置注解
        jsr250Enabled = true // JSR-250 提供的安全控制注解
)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
    /**
     * gnol 系统_用户表 Service 实现
     */
    @Resource(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean("sha1PasswordEncoder")
    @Primary
    public PasswordEncoder sha1PasswordEncoder() {
        return new SHA1PasswordEncoder(2);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(sha1PasswordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 关闭 csrf 跨站访问拦截
                .headers().frameOptions().disable() // 不允许 iframe 内呈现
                .and().cors() // 支持跨域请求
                .and().authorizeRequests()
                .antMatchers("/eureka/apps/**"/*eureka 心跳相关*/, "/static/**"/*静态资源*/, "/favicon.ico", "/index"/*去登录页面*/,
                        "/loginParam"/*登录页参数*/)
                .permitAll() // 免授权请求配置
                .anyRequest().authenticated() // 其余所有请求都需要授权
                .and().formLogin().usernameParameter("userAccount").passwordParameter("password").loginPage("/index")
                .loginProcessingUrl("/login") // 配置以 form 表单形式登录参数及请求路径
                .successHandler(new AuthenticationSuccessHandler() { // 登录成功后操作
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
                        logger.debug("{} 用户在 {} 地址登录成功！", authentication.getName(), request.getRemoteAddr());
                        response.sendRedirect("/main");
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                            AuthenticationException exception) throws IOException, ServletException {
                        response.sendRedirect("/index?error=" + exception.getMessage());
                    }
                }).and().logout().logoutSuccessUrl("/index") // 登出授权
                .invalidateHttpSession(true).clearAuthentication(true)
        // .httpBasic(); 以弹框方式认证
        // .accessDecisionManager(new CustomAccessDecisionManager()) // 自定义权限决策处理
        ;
    }

}