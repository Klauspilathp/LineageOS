package com.d7c.springboot.client.config;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.net.tools.IPUtil;
import com.d7c.redis.spring.boot.autoconfigure.RedisService;
import com.d7c.springboot.client.services.sys.SysSessionService;
import com.d7c.springboot.client.services.sys.SysUserService;
import com.d7c.springboot.common.dos.sys.SysSession;
import com.d7c.springboot.common.dos.sys.SysUser;
import com.d7c.springboot.common.enums.sys.LoginStatusEnum;

/**
 * @Title: SingleWebSecurityConfiguration
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午3:49:51
 * @Description: 单机 web security 配置
 */
@ConditionalOnProperty( // 存在对应配置信息时初始化该配置类
        prefix = "d7c.authorization.server", // 配置前缀 d7c.authorization.server
        name = "enabled", // 配置名称
        havingValue = "true", // 与配置值比较，如果相等则配置生效
        matchIfMissing = true // 默认配置值，即默认开启此配置
)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( // 开启 security 注解
        prePostEnabled = false, // 基于表达式进行方法级别的访问控制
        securedEnabled = false, // security 内置注解
        jsr250Enabled = true // JSR-250 提供的安全控制注解
)
public class SingleWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SingleWebSecurityConfiguration.class);
    /**
     * d7c 系统_用户表 Service 实现
     */
    @Resource(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    /**
     * d7c 系统_用户详情权限临时表，用户在登录，下线等情况下触发插入、删除、更新操作，然后以此表关联业务表查询指定范围内的 Service 实现
     */
    @Resource(name = "sysSessionServiceImpl")
    private SysSessionService sysSessionService;
    /**
     * d7c 系统_用户表 Service 实现
     */
    @Resource(name = "sysUserServiceImpl")
    private SysUserService sysUserService;
    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;
    /**
     * redis 中持久化记住我 token 实现
     */
    @Resource(name = "redisPersistentTokenRepository")
    private RedisPersistentTokenRepository redisPersistentTokenRepository;
    /**
     * redis 缓存服务实现
     */
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;
    /**
     * SHA1 的 PasswordEncoder 加密实现类
     */
    @Resource(name = "sha1PasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    // org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl.CREATE_TABLE_SQL
    @Bean("jdbcTokenRepositoryImpl")
    public PersistentTokenRepository jdbcTokenRepositoryImpl() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 首次设置为 true 创建表
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 关闭 csrf 跨站访问拦截
                .headers().frameOptions().disable() // 不允许 iframe 内呈现
                .and().cors() // 支持跨域请求
                .and().authorizeRequests()
                .antMatchers("/eureka/apps/**"/*eureka 心跳相关*/, "/static/**"/*静态资源*/, "/favicon.ico", "/index"/*去登录页面*/,
                        "/verifyCode" /*验证码*/, "/loginParam"/*登录页参数*/, "/actuator", "/actuator/**"/*监控相关*/)
                .permitAll() // 免授权请求配置
                .anyRequest().authenticated() // 其余所有请求都需要授权
                .and().formLogin().usernameParameter("userAccount").passwordParameter("password").loginPage("/index")
                .loginProcessingUrl("/login") // 配置以 form 表单形式登录参数及请求路径
                .successHandler(new AuthenticationSuccessHandler() { // 登录成功后操作
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
                        logger.debug("{} 用户在 {} 地址登录成功！", authentication.getName(), request.getRemoteAddr());
                        // 根据账号查询用户信息
                        SysUser sysUser = sysUserService.getSysUserByUserAccount(authentication.getName());

                        // 更新登录状态
                        SysUser user = new SysUser();
                        user.setUserId(sysUser.getUserId());
                        user.setLoginStatus(LoginStatusEnum.ON_LINE.getKey());
                        user.setLoginTime(new Date());
                        user.setLoginIp(IPUtil.getIpAddr((HttpServletRequest) request));
                        sysUserService.updateByLogin(user);

                        // 更新 session 信息
                        SysSession sysSession = new SysSession(request.getSession().getId(), sysUser);
                        // 设置地域信息及角色名
                        sysSession.setLoginStatus(user.getLoginStatus());
                        sysSession.setLoginTime(user.getLoginTime());
                        sysSession = sysSessionService.updateUserAddress(sysSession);
                        redisService.addObject(redisService.generateKey(D7cConstant.SESSION_USER, sysUser.getUserId()),
                                redisPersistentTokenRepository.getTokenExpiration(), sysSession);

                        // 重定向到首页
                        response.sendRedirect("/main");
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                            AuthenticationException exception) throws IOException, ServletException {
                        response.sendRedirect("/index?error=" + exception.getMessage());
                    }
                }).and().rememberMe().tokenRepository(redisPersistentTokenRepository) // new InMemoryTokenRepositoryImpl()、jdbcTokenRepositoryImpl()
                .tokenValiditySeconds(StringUtil.toInt(redisPersistentTokenRepository.getTokenExpiration())) // 记住我一天
                .and().logout().logoutSuccessUrl("/index") // 登出授权
                .invalidateHttpSession(true).clearAuthentication(true)
        // .httpBasic(); 以弹框方式认证
        // .accessDecisionManager(new CustomAccessDecisionManager()) // 自定义权限决策处理
        ;
    }

}