package com.gnol.springboot.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Title: WebMvcConfig
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年6月8日 下午5:20:00
 * @Description: 自定义视图映射
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/testmvc").setViewName("/abc");
    }

}