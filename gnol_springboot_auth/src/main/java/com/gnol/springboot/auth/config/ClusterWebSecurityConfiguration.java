package com.gnol.springboot.auth.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gnol.jwt.spring.boot.autoconfigure.JwtRsaUtil;
import com.gnol.springboot.auth.daos.security.ExtSecurityKeyDao;

/**
 * @Title: ClusterWebSecurityConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午3:49:51
 * @Description: 集群 web security 配置
 */
@Configuration
@EnableWebSecurity
public class ClusterWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * gnol 系统_用户表 Service 实现
     */
    @Resource(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    /**
     * gnol 系统安全模块_用户或系统密钥扩展 Dao
     */
    @Resource(name = "extSecurityKeyDao")
    private ExtSecurityKeyDao securityKeyDao;
    /**
     * jwt 采用 RSA 方式加密的 token 工具类
     */
    @Autowired
    private JwtRsaUtil JwtRsaUtil;

    @Bean("sha1PasswordEncoder")
    @Primary
    public PasswordEncoder sha1PasswordEncoder() {
        return new SHA1PasswordEncoder(2);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(sha1PasswordEncoder());
    }

    /**
     * 此处采用
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 关闭 csrf 跨站访问拦截
                .authorizeRequests()
                .antMatchers("/eureka/apps/**"/*eureka 心跳相关*/, "/actuator", "/actuator/**"/*监控相关*/,
                        "/authentication"/*认证授权*/, "/validate"/*验证权限*/, "/unsubscribe"/*注销授权*/)
                .permitAll() // 免授权请求配置
                .anyRequest().authenticated() // 其余所有请求都需要授权
                .and().addFilter(new JwtLoginFilter(super.authenticationManager(), securityKeyDao, JwtRsaUtil)) // 登录授权过滤器
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用 HttpSession
    }

}