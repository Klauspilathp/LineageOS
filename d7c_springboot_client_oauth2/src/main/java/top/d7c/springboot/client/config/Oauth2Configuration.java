package top.d7c.springboot.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: Oauth2Configuration
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月20日 上午9:44:31
 * @Description: 资源授权服务器配置类
 */
@Configuration
public class Oauth2Configuration {

    /**
     * d7c 系统自定义属性
     */
    @Bean(name = "d7cProperties")
    @ConditionalOnMissingBean(D7cProperties.class)
    public D7cProperties d7cProperties() {
        return new D7cProperties();
    }

}