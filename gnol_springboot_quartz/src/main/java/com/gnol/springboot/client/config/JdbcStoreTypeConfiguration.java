package com.gnol.springboot.client.config;

import java.util.Map;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSourceInitializer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.gnol.mybatis.spring.boot.autoconfigure.DruidDataSourceWrapper;
import com.gnol.plugins.core.StringUtil;

/**
 * @Title: JdbcStoreTypeConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月15日 下午2:57:09
 * @Description: quartz jdbc 数据存储配置
 * 参考 org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration.JdbcStoreTypeConfiguration
 * org.springframework.scheduling.quartz.SchedulerFactoryBean.572L javax.sql.DataSource 已经被初始化，
 * 所以不能在初始化 quartz 指定的 javax.sql.DataSource。
 */
@ConditionalOnProperty(prefix = "spring.quartz", name = "job-jdbc-store", havingValue = "true")
@ConditionalOnClass(QuartzProperties.class)
@Configuration
public class JdbcStoreTypeConfiguration {
    /**
     * Configuration properties for the Quartz Scheduler integration.
     */
    @Autowired
    private QuartzProperties properties;

    @Bean(name = "quartzDataSource")
    public DataSource quartzDataSource() {
        Map<String, String> props = properties.getProperties();
        if (props == null || props.isEmpty()) {
            throw new IllegalArgumentException("the quartz database storage parameter is empty");
        }
        String dataSourceName = props.get("org.quartz.jobStore.dataSource");
        if (StringUtil.isBlank(dataSourceName)) {
            throw new IllegalArgumentException(
                    "the quartz spring.quartz.properties.org.quartz.jobStore.dataSource parameter is empty");
        }
        DataSourceProperties basicProperties = new DataSourceProperties();
        basicProperties.setDriverClassName(props.get("org.quartz.dataSource." + dataSourceName + ".driver"));
        basicProperties.setUrl(props.get("org.quartz.dataSource." + dataSourceName + ".URL"));
        basicProperties.setUsername(props.get("org.quartz.dataSource." + dataSourceName + ".user"));
        basicProperties.setPassword(props.get("org.quartz.dataSource." + dataSourceName + ".password"));
        return new DruidDataSourceWrapper(basicProperties);
    }

    @Bean
    @Order(0)
    public SchedulerFactoryBeanCustomizer dataSourceCustomizer(@Qualifier("quartzDataSource") DataSource dataSource,
            @QuartzDataSource ObjectProvider<DataSource> quartzDataSource,
            ObjectProvider<PlatformTransactionManager> transactionManager) {
        return (schedulerFactoryBean) -> {
            DataSource dataSourceToUse = getDataSource(dataSource, quartzDataSource);
            schedulerFactoryBean.setDataSource(dataSourceToUse);
            PlatformTransactionManager txManager = transactionManager.getIfUnique();
            if (txManager != null) {
                schedulerFactoryBean.setTransactionManager(txManager);
            }
        };
    }

    private DataSource getDataSource(DataSource dataSource, ObjectProvider<DataSource> quartzDataSource) {
        DataSource dataSourceIfAvailable = quartzDataSource.getIfAvailable();
        return (dataSourceIfAvailable != null) ? dataSourceIfAvailable : dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public QuartzDataSourceInitializer quartzDataSourceInitializer(@Qualifier("quartzDataSource") DataSource dataSource,
            @QuartzDataSource ObjectProvider<DataSource> quartzDataSource, ResourceLoader resourceLoader) {
        DataSource dataSourceToUse = getDataSource(dataSource, quartzDataSource);
        return new QuartzDataSourceInitializer(dataSourceToUse, resourceLoader, properties);
    }

    /**
     * Additional configuration to ensure that {@link SchedulerFactoryBean} and
     * {@link Scheduler} beans depend on any beans that perform data source initialization.
     */
    @Configuration
    static class QuartzSchedulerDependencyConfiguration {

        @Bean
        public static SchedulerDependsOnBeanFactoryPostProcessor quartzSchedulerDataSourceInitializerDependsOnBeanFactoryPostProcessor() {
            return new SchedulerDependsOnBeanFactoryPostProcessor(QuartzDataSourceInitializer.class);
        }

    }

    /**
     * {@link AbstractDependsOnBeanFactoryPostProcessor} for Quartz {@link Scheduler} and {@link SchedulerFactoryBean}.
     */
    private static class SchedulerDependsOnBeanFactoryPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

        SchedulerDependsOnBeanFactoryPostProcessor(Class<?>... dependencyTypes) {
            super(Scheduler.class, SchedulerFactoryBean.class, dependencyTypes);
        }

    }

}