package com.gnol.springboot.common.configurations.ehcache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @Title: EhcacheConfiguration
 * @Package: com.gnol.springboot.common.configurations.ehcache
 * @author: 吴佳隆
 * @date: 2020年7月5日 下午3:39:28
 * @Description: ehcache 配置类
 */
@ConditionalOnProperty(prefix = "spring.ehcache", name = "enabled", havingValue = "true")
@Configuration
@EnableCaching
public class EhcacheConfiguration {

    /**
     * EhCacheManagerFactoryBean 配置
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

    /**
     * EhCacheCacheManager 配置
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
    }

    /**
     * EhcacheServiceImpl 配置
     */
    @Bean
    public EhcacheService ehcacheServiceImpl(EhCacheCacheManager ehCacheCacheManager) {
        EhcacheServiceImpl ehcacheService = new EhcacheServiceImpl(ehCacheCacheManager.getCacheManager());
        ehcacheService.getCache("ehcache0");
        return ehcacheService;
    }

}