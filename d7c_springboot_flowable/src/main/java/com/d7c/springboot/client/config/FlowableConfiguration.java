package com.d7c.springboot.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title: FlowableConfiguration
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月26日 下午1:06:37
 * @Description: flowable 项目配置类
 */
@Configuration
public class FlowableConfiguration {

    /**
     * d7c 系统自定义属性
     */
    @Bean(name = "d7cProperties")
    @ConditionalOnMissingBean(D7cProperties.class)
    public D7cProperties d7cProperties() {
        return new D7cProperties();
    }

    /**
     * 静态文件资源目录映射为可以 Rest 方式访问的路径
     */
    @Bean(name = "staticResoucesPath")
    public WebMvcConfigurer staticResoucesPath() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/processes/**").addResourceLocations("classpath:/processes/");
                registry.addResourceHandler("/modeler/**").addResourceLocations("classpath:/modeler/");
                registry.addResourceHandler("/idm/**").addResourceLocations("classpath:/idm/");
                WebMvcConfigurer.super.addResourceHandlers(registry);
            }
        };
    }

    // --- 支持跨域的两种配置方式
    @Bean(name = "corsFilter")
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

    /*@Bean(name = "corsConfigurer")
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