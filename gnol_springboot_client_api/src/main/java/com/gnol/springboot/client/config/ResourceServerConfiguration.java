package com.gnol.springboot.client.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * @Title: ResourceServerConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月22日 下午2:09:54
 * @Description: oauth2 资源服务器配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /**
     * 发现系统中登记的服务实例对象
     */
    @Autowired
    private Registration registration;
    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * token 持久化策略
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 指定当前资源服务器的 id 和 token 存储方案 
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(registration.getServiceId()).tokenStore(jdbcTokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 指定不同请求方式访问资源所需权限，一般查询是 read，其余是 write。
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')").and().headers() // 添加跨域请求头
                .addHeaderWriter((request, response) -> {
                    response.addHeader("Access-Control-Allow-Origin", "*"); // 允许跨域
                    if (request.getMethod().equals("OPTIONS")) { // 如果是跨域的预检请求，则原封不动向下传达请求头信息
                        response.setHeader("Access-Control-Allow-Methods",
                                request.getHeader("Access-Control-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers",
                                request.getHeader("Access-Control-Request-Headers"));
                    }
                });
    }

}