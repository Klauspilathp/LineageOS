package com.d7c.springboot.common.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

import com.d7c.plugins.core.PageResult;

/**
 * @Title: CustomAbstractHealthIndicator
 * @Package: com.d7c.springboot.common.actuator.health
 * @author: 吴佳隆
 * @date: 2021年1月4日 下午1:45:57
 * @Description: 自定义 actuator 健康检查，访问 /actuator/health 接口会多出 custom-abstract-health 节点的健康信息，
 * 比 org.springframework.boot.actuate.health.HealthIndicator 接口强大。
 */
@Component("custom-abstract-health")
public class CustomAbstractHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        PageResult result = check();
        if (result.isOk()) {
            builder.up().withDetail("details", result.toString()).build();
        }
        builder.down().withDetail("details", result.toString()).build();
    }

    /**
     * 健康检查逻辑
     */
    private PageResult check() {
        // TODO perform some specific health check
        return PageResult.ok();
    }

}