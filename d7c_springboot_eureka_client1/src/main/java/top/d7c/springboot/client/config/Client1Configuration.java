package top.d7c.springboot.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: Client1Configuration
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年8月2日 下午4:59:58
 * @Description: eureka 项目配置类
 */
@Configuration
public class Client1Configuration {

    @Bean(name = "githubClientResources")
    @ConfigurationProperties(prefix = "github")
    public ClientResources githubClientResources() {
        return new ClientResources();
    }

    @Bean(name = "d7cEurekaClient1ClientResources")
    @ConfigurationProperties(prefix = "d7c-eureka-client1")
    public ClientResources d7cEurekaClient1ClientResources() {
        return new ClientResources();
    }

}