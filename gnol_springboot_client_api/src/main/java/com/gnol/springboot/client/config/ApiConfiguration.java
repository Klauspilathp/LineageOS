package com.gnol.springboot.client.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Title: ApiConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月26日 下午1:06:37
 * @Description: api 项目配置类
 */
@Configuration
public class ApiConfiguration {

    /**
     * oauth2 rest 服务客户端
     */
    @Bean(name = "oAuth2RestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext context, OAuth2ProtectedResourceDetails details) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(details, context);
        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeProvider.setStateMandatory(false);
        AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(authCodeProvider));
        restTemplate.setAccessTokenProvider(provider);
        return restTemplate;
    }

    /**
     * gnol 系统自定义属性
     */
    @Bean(name = "gnolProperties")
    @ConditionalOnMissingBean(GnolProperties.class)
    public GnolProperties gnolProperties() {
        return new GnolProperties();
    }

    // --- 支持跨域的两种配置方式
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许 cookies 跨域
        config.addAllowedMethod("*"); // 允许提交请求的方法，* 表示全部允许
        config.addAllowedOrigin("*"); // 允许向该服务器提交请求的 URI，* 表示全部允许
        config.addAllowedHeader("*"); // 允许访问的头信息，* 表示全部
        config.setMaxAge(18000L); // 预检请求的缓存时间（秒），即在这个时间段内对相同的跨域请求不会再预检
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 拦截所有权请求
                        .allowedMethods("*") // 允许提交请求的方法，* 表示全部允许
                        .allowedOrigins("*") // 允许向该服务器提交请求的 URI，* 表示全部允许
                        .allowCredentials(true) // 允许 cookies 跨域
                        .allowedHeaders("*") // 允许访问的头信息，* 表示全部
                        .maxAge(18000L); // 预检请求的缓存时间（秒），即在这个时间段内对相同的跨域请求不会再预检
            }
        };
    }*/

}