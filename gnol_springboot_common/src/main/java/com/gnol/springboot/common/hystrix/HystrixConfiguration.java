package com.gnol.springboot.common.hystrix;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * @Title: HystrixConfiguration
 * @Package: com.gnol.springboot.common.hystrix
 * @author: 吴佳隆
 * @date: 2020年6月20日 下午4:55:20
 * @Description: 配置访问 /hystrix.stream 的 servlet
 */
@Configuration
public class HystrixConfiguration {

    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> hystrixMetricsStreamServlet() {
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> servletRegistrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>();
        servletRegistrationBean.setServlet(hystrixMetricsStreamServlet);
        servletRegistrationBean.addUrlMappings("/hystrix.stream");
        servletRegistrationBean.setName("hystrixMetricsStreamServlet");
        return servletRegistrationBean;
    }

}