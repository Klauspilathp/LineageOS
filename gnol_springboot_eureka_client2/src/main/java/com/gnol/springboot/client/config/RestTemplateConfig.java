package com.gnol.springboot.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Title: RestTemplateConfig
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年6月17日 上午9:22:35
 * @Description: RestTemplate 是 spring 提供的用于访问 rest 服务的客户端。
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    // @LoadBalanced // ribbon 的负载均衡注解
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}