package com.gnol.springboot.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * @Title: ZuulConfiguration
 * @Package: com.gnol.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午7:15:37
 * @Description: zuul 项目自动配置
 */
@Configuration
public class ZuulConfiguration {

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }

    /**
     * 解决如果遇到已经在配置中声明了 spring.sleuth.sampler.percentage=1.0，而在 zipkin-traces 中还看不到数据的问题。
     */
    /*@Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }*/

}