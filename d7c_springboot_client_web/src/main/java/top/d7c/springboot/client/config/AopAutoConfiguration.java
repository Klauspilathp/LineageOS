package top.d7c.springboot.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Title: AopAutoConfiguration
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月2日 下午2:26:53
 * @Description: aop 自动代理
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopAutoConfiguration {

    // 设置需要初始化的 bean

}