package com.d7c.springboot.common.configurations.hystrix;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * @Title: HystrixConfiguration
 * @Package: com.d7c.springboot.common.configurations.hystrix
 * @author: 吴佳隆
 * @date: 2020年6月20日 下午4:55:20
 * @Description: 配置访问 /hystrix.stream 的 servlet
 */
@ConditionalOnProperty(prefix = "spring.hystrix.stream", name = "enabled", havingValue = "true")
@Configuration
public class HystrixConfiguration {

    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> hystrixMetricsStreamServlet() {
        ServletRegistrationBean<HystrixMetricsStreamServlet> servletRegistrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>(
                new HystrixMetricsStreamServlet());
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.addUrlMappings("/hystrix.stream");
        servletRegistrationBean.setName("hystrixMetricsStreamServlet");
        return servletRegistrationBean;
    }

}