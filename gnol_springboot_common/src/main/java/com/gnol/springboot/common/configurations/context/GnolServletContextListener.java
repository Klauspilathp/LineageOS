package com.gnol.springboot.common.configurations.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.annotation.Configuration;

/**
 * @Title: GnolServletContextListener
 * @Package: com.gnol.springboot.common.configurations.context
 * @author: 吴佳隆
 * @date: 2020年7月1日 下午4:56:38
 * @Description: 系统启停监听器
 */
@Configuration
public class GnolServletContextListener implements ServletContextListener {

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