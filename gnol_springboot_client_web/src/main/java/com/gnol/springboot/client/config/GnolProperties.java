package com.gnol.springboot.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Title: GnolProperties
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月14日 上午9:18:53
 * @Description: gnol 系统自定义属性
 */
@ConditionalOnProperty( // 存在对应配置信息时初始化该配置类
        prefix = "gnol", // 配置前缀 gnol
        name = "enabled", // 配置名称
        havingValue = "true", // 与配置值比较，如果相等则配置生效
        matchIfMissing = true // 默认配置值，即默认开启此配置
)
@ConfigurationProperties(prefix = "gnol")
public class GnolProperties {
    /**
     * 系统名称
     */
    private String systemName;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}