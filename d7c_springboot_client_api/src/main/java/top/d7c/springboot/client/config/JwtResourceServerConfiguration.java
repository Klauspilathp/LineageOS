package top.d7c.springboot.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import top.d7c.oauth2.springboot.CustomAccessTokenConverter;
import top.d7c.oauth2.springboot.CustomWebResponseExceptionTranslator;

/**
 * @Title: JdbcResourceServerConfiguration
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月22日 下午2:09:54
 * @Description: jwt 对称加密储存策略存储授权码 oauth2 资源服务器配置
 */
// @Configuration
// @EnableResourceServer
public class JwtResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /**
     * 资源服务器属性配置
     */
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    /**
     * 自定义异常转化器
     */
    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;
    /**
     * oauth2 认证端点
     */
    @Autowired
    private OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint;
    /**
     * 授权失败处理
     */
    @Autowired
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    /**
     * 自定义异常转化器
     */
    @Bean
    public CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator() {
        return new CustomWebResponseExceptionTranslator();
    }

    /**
     * oauth2 认证端点
     */
    @Bean
    public OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint() {
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setExceptionTranslator(customWebResponseExceptionTranslator);
        return oAuth2AuthenticationEntryPoint;
    }

    /**
     * 授权失败处理
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler = new OAuth2AccessDeniedHandler();
        oAuth2AccessDeniedHandler.setExceptionTranslator(customWebResponseExceptionTranslator);
        return oAuth2AccessDeniedHandler;
    }

    /**
     * jwt token 解析工具
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue()); // 对称密钥
        tokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
        return tokenConverter;
    }

    /**
     * token 持久化策略
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 指定当前资源服务器的 id 和 token 存储方案 
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceServerProperties.getServiceId()) // 当前资源服务器的 id
                .authenticationEntryPoint(oAuth2AuthenticationEntryPoint) // oauth2 认证端点
                .accessDeniedHandler(oAuth2AccessDeniedHandler) // 授权失败处理
                .tokenStore(jwtTokenStore()).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 指定不同请求方式访问资源所需权限，一般查询是 read，其余是 write。
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')").anyRequest().authenticated()
                .and().csrf().disable().headers() // 添加跨域请求头
                .addHeaderWriter((request, response) -> {
                    response.addHeader("Access-Control-Allow-Origin", "*"); // 允许跨域
                    if (request.getMethod().equals("OPTIONS")) { // 如果是跨域的预检请求，则原封不动向下传达请求头信息
                        response.setHeader("Access-Control-Allow-Methods",
                                request.getHeader("Access-Control-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers",
                                request.getHeader("Access-Control-Request-Headers"));
                    }
                }).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 有需要时才生成 session
    }

}