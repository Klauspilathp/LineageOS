package top.d7c.springboot.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * @Title: WebSecurityConfiguration
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午1:00:39
 * @Description: Web Security 安全控制。
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private FlowableOncePerRequestFilter flowableOncePerRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(flowableOncePerRequestFilter, SecurityContextPersistenceFilter.class).authorizeRequests()
                .anyRequest().authenticated().and().csrf().disable();
    }

}
