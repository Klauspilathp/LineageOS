package com.d7c.springboot.client.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @Title: JobFactory
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年5月8日 下午7:06:13
 * @Description: 将 Job 的实例化交给 IOC 进行管理，解决 spring 不能在 quartz 中注入 bean 的问题。
 */
// @Component
public class JobFactory extends AdaptableJobFactory {
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    // 重写创建 Job 任务的实例方法
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        // 通过以下方式，解决 Job 任务无法使用 Spring 中的 Bean 问题
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}