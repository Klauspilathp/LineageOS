package top.d7c.springboot.common.configurations.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import top.d7c.springboot.common.configurations.exception.CustomResponseErrorHandler;

/**
 * @Title: RestTemplateConfiguration
 * @Package: top.d7c.springboot.common.configurations.rest
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午7:12:05
 * @Description: RestTemplate 是 spring 提供的用于访问 rest 服务的客户端。
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean(name = "loadBalancedRestTemplate")
    @LoadBalanced // ribbon 的负载均衡注解，org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration
    @Primary
    public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

}