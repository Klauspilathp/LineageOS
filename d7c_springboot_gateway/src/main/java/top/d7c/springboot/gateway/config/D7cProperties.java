package top.d7c.springboot.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Title: D7cProperties
 * @Package: top.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2021年1月4日 下午4:24:51
 * @Description: d7c 系统自定义属性
 */
@ConditionalOnProperty( // 存在对应配置信息时初始化该配置类
        prefix = "d7c", // 配置前缀 d7c
        name = "enabled", // 配置名称
        havingValue = "true", // 与配置值比较，如果相等则配置生效
        matchIfMissing = true // 默认配置值，即默认开启此配置
)
@ConfigurationProperties(prefix = "d7c")
public class D7cProperties {

}