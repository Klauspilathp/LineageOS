package com.gnol.springboot.auth.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gnol.jwt.spring.boot.autoconfigure.JwtRsaUtil;
import com.gnol.springboot.auth.daos.security.ExtSecurityKeyDao;
import com.gnol.springboot.auth.daos.sys.ExtSysMenuDao;

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
    /**
     * gnol 系统菜单表扩展 Dao
     */
    @Resource(name = "extSysMenuDao")
    private ExtSysMenuDao sysMenuDao;

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
                .authorizeRequests()
                .antMatchers("/eureka/apps/**"/*eureka 心跳相关*/, "/actuator", "/actuator/**"/*监控相关*/, "/login"/*认证授权*/,
                        "/validate"/*验证权限*/, "/logout"/*注销授权*/)
                .permitAll() // 免授权请求配置
                .anyRequest().authenticated() // 其余所有请求都需要授权
                .and().addFilter(new JwtLoginFilter(super.authenticationManager(), securityKeyDao, JwtRsaUtil)) // 登录授权过滤器
                .addFilter(new JwtAuthenticationFilter(super.authenticationManager(), securityKeyDao, JwtRsaUtil,
                        sysMenuDao)) // token 检验过滤器，在授权服务器中，此过滤器不是必须的
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用 HttpSession
        // disable page caching
        // http.headers().frameOptions().sameOrigin().cacheControl();
    }

}