package com.d7c.springboot.client.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.d7c.jwt.springboot.autoconfigure.JwtRsaUtil;
import com.d7c.springboot.client.services.sys.SysMenuService;

/**
 * @Title: ClusterWebSecurityConfiguration
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午3:49:51
 * @Description: 集群 web security 配置，认证服务器为 d7c_springboot_auth。
 */
/*@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( // 开启 security 注解
        prePostEnabled = false, // 基于表达式进行方法级别的访问控制
        securedEnabled = false, // security 内置注解
        jsr250Enabled = true // JSR-250 提供的安全控制注解
)*/
public class ClusterWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * d7c 系统自定义属性
     */
    @Resource(name = "d7cProperties")
    private D7cProperties d7cProperties;
    /**
     * jwt 采用 RSA 方式加密的 token 工具类
     */
    @Autowired
    private JwtRsaUtil JwtRsaUtil;
    /**
     * d7c 系统菜单表 Service
     */
    @Resource(name = "sysMenuServiceImpl")
    private SysMenuService sysMenuService;

    /**
     * SHA1 的 PasswordEncoder 加密实现类
     */
    @Resource(name = "sha1PasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 关闭 csrf 跨站访问拦截
                .authorizeRequests()
                .antMatchers("/eureka/apps/**"/*eureka 心跳相关*/, "/static/**"/*静态资源*/, "/favicon.ico", "/index"/*去登录页面*/,
                        "/verifyCode" /*验证码*/, "/loginParam"/*登录页参数*/, "/actuator", "/actuator/**"/*监控相关*/,
                        "/login"/*认证授权*/, "/logout"/*注销授权*/)
                .permitAll() // 免授权请求配置
                .anyRequest().authenticated() // 其余所有请求都需要授权
                .and()
                .addFilter(new JwtAuthenticationFilter(super.authenticationManager(), d7cProperties, JwtRsaUtil,
                        sysMenuService)) // token 检验过滤器
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用 HttpSession
    }

}