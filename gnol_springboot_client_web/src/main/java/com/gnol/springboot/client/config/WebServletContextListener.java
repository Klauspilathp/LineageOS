package com.gnol.springboot.client.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.annotation.Configuration;

/**
 * @Title: WebServletContextListener
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月1日 下午4:56:38
 * @Description: 系统启停监听器
 */
@Configuration
public class WebServletContextListener implements ServletContextListener {

    /**
     * 容器初始化
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    /**
     * 容器销毁
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /*WebApplicationContext webApplicationContext = (WebApplicationContext) sce.getServletContext()
                .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);*/
    }

}