package com.gnol.springboot.client.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gnol.oauth2.spring.boot.SHA1PasswordEncoder;

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

    /**
     * SHA1 的 PasswordEncoder 加密实现类
     */
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
        logger.debug("Using custom configure(HttpSecurity).");
        http.csrf().disable() // 关闭 csrf 跨站访问拦截
                .headers().frameOptions().disable() // 不允许 iframe 内呈现
                .and().cors() // 支持跨域请求
                .and().authorizeRequests()
                .antMatchers("/eureka/apps/**"/*eureka 心跳相关*/, "/actuator", "/actuator/**"/*监控相关*/
                ).permitAll() // 免授权请求配置
                .anyRequest().authenticated() // 其余所有请求都需要授权
                .and().formLogin().loginProcessingUrl("/login") // 表单登录
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // 有需要时才生成 session，使用授权码模式必须生成 session
        // disable page caching
        // http.headers().frameOptions().sameOrigin().cacheControl();
    }

    /**
     * 将 AuthenticationManager 放入 Spring IOC 容器中，oauth2 认证服务密码模式要使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        logger.debug("create AuthenticationManager");
        return super.authenticationManagerBean();
    }

}