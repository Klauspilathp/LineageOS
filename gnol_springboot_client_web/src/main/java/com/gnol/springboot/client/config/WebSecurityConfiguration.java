package com.gnol.springboot.client.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.gnol.plugins.core.StringUtil;
import com.gnol.springboot.client.services.sys.SysUserService;
import com.gnol.springboot.common.dos.sys.SysUser;

/**
 * @Title: WebSecurityConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午3:49:51
 * @Description: web security 配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
    /**
     * gnol 系统_用户表 Service 实现
     */
    @Resource(name = "sysUserServiceImpl")
    private SysUserService sysUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭 csrf
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 配置登录页面
        http.formLogin().loginPage("/toLogin").permitAll();
        // 配置登录成功后的操作
        http.formLogin().successHandler(new LoginSuccessHandler());
        // 用户权限不足处理器
        http.exceptionHandling().accessDeniedHandler(new WebAccessDeniedHandler());
        // 登出授权
        http.logout().permitAll();
        // 授权配置
        http.authorizeRequests().antMatchers("/js/**", "/css/**", "/images/**").permitAll().anyRequest()
                .authenticated();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if (StringUtil.isBlank(username)) {
                    throw new UsernameNotFoundException("username cannot be empty !");
                }
                SysUser sysUser = sysUserService.getSysUserByUserAccount(username);
                if (sysUser == null) {
                    throw new UsernameNotFoundException("the user does not exist !");
                }
                return (UserDetails) sysUser;
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * @Title: LoginSuccessHandler
     * @Package: com.gnol.springboot.client.config
     * @author: 吴佳隆
     * @date: 2020年7月6日 下午4:01:57
     * @Description: 登录成功处理器
     */
    private class LoginSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            logger.info("{} 在 IP {} 登录成功！", authentication.getName(), request.getRemoteAddr());
            // 重定向到登录页面
            response.sendRedirect("/login");
        }
    }

    /**
     * @Title: WebAccessDeniedHandler
     * @Package: com.gnol.springboot.client.config
     * @author: 吴佳隆
     * @date: 2020年7月6日 下午4:01:31
     * @Description: 用户权限不足处理器
     */
    private class WebAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                AccessDeniedException accessDeniedException) throws IOException, ServletException {
            logger.info("{} 在 IP {} 访问没有权限的 {} 接口！", SecurityContextHolder.getContext().getAuthentication().getName(),
                    request.getRemoteAddr(), request.getRequestURI());
            response.sendRedirect("/unauthorized");
        }
    }

}