package com.gnol.springboot.client.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Title: JdbcResourceServerConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月22日 下午2:09:54
 * @Description: jwt 非对称（密钥对）加密储存策略存储授权码 oauth2 资源服务器配置
 */
@Configuration
@EnableResourceServer
public class JwtKeyPairResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /**
     * 资源服务器属性配置
     */
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    /**
     * 认证服务器属性配置
     */
    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;
    /**
     * RestTemplate 是 spring 提供的用于访问 rest 服务的客户端
     */
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 自定义异常转化器
     */
    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;
    /**
     * oauth2 认证端点
     */
    @Autowired
    private OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint;
    /**
     * 授权失败处理
     */
    @Autowired
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    /**
     * 认证服务器属性配置
     */
    @Bean
    public AuthorizationServerProperties authorizationServerProperties() {
        return new AuthorizationServerProperties();
    }

    /**
     * 自定义异常转化器
     */
    @Bean
    public CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator() {
        return new CustomWebResponseExceptionTranslator();
    }

    /**
     * oauth2 认证端点
     */
    @Bean
    public OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint() {
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setExceptionTranslator(customWebResponseExceptionTranslator);
        return oAuth2AuthenticationEntryPoint;
    }

    /**
     * 授权失败处理
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler = new OAuth2AccessDeniedHandler();
        oAuth2AccessDeniedHandler.setExceptionTranslator(customWebResponseExceptionTranslator);
        return oAuth2AccessDeniedHandler;
    }

    /**
     * jwt token 解析工具
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
        String key = null;
        Resource resource = new ClassPathResource(authorizationServerProperties.getJwt().getKeyStore());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            key = br.lines().collect(Collectors.joining("\r\n"));
        } catch (IOException ioe) {
            ObjectMapper objectMapper = new ObjectMapper();
            String result = restTemplate.getForObject(authorizationServerProperties.getTokenKeyAccess(), String.class);
            try {
                Map map = objectMapper.readValue(result, Map.class);
                key = map.get("value").toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tokenConverter.setVerifierKey(key);
        return tokenConverter;
    }

    /**
     * token 持久化策略
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 指定当前资源服务器的 id 和 token 存储方案 
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceServerProperties.getServiceId()) // 当前资源服务器的 id
                .authenticationEntryPoint(oAuth2AuthenticationEntryPoint) // oauth2 认证端点
                .accessDeniedHandler(oAuth2AccessDeniedHandler) // 授权失败处理
                .tokenStore(jwtTokenStore()).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 指定不同请求方式访问资源所需权限，一般查询是 read，其余是 write。
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')").anyRequest().authenticated()
                .and().csrf().disable().headers() // 添加跨域请求头
                .addHeaderWriter((request, response) -> {
                    response.addHeader("Access-Control-Allow-Origin", "*"); // 允许跨域
                    if (request.getMethod().equals("OPTIONS")) { // 如果是跨域的预检请求，则原封不动向下传达请求头信息
                        response.setHeader("Access-Control-Allow-Methods",
                                request.getHeader("Access-Control-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers",
                                request.getHeader("Access-Control-Request-Headers"));
                    }
                }).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 有需要时才生成 session
    }

}