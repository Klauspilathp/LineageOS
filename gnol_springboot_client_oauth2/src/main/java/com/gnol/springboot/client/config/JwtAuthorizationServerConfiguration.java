package com.gnol.springboot.client.config;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Title: JwtAuthorizationServerConfiguration
 * @Package: com.gnol.springboot.oauth2.config
 * @author: 吴佳隆
 * @date: 2020年7月21日 下午6:51:30
 * @Description: jwt 对称加密储存策略 oauth2 授权服务器策略
 */
@Configuration
@EnableAuthorizationServer
public class JwtAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * 数据源
     */
    @Resource(name = "masterDataSource")
    private DataSource dataSource;
    /**
     * 认证服务器属性配置
     */
    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;
    /**
     * 认证管理对象
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 认证服务器属性配置
     */
    @Bean
    public AuthorizationServerProperties authorizationServerProperties() {
        return new AuthorizationServerProperties();
    }

    /**
     * BCrypt 的 PasswordEncoder 加密实现类
     */
    @Bean("bCryptPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 客户端信息实现
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 授权码模式数据来源
     */
    @Bean
    public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 授权信息保存策略
     */
    @Bean
    public ApprovalStore jdbcApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * jwt token 解析工具
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(authorizationServerProperties.getJwt().getKeyValue()); // 对称密钥
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
     * 令牌增强
     */
    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    /**
     * 授权服务器安全策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(bCryptPasswordEncoder()).tokenKeyAccess("permitAll()") // 所有客户端都能请求 /oauth/token_key 端点
                .checkTokenAccess("isAuthenticated()") // 已验证的客户端才能请求 /oauth/check_token 端点
                .allowFormAuthenticationForClients(); // 允许表单方式认证
    }

    /**
     * 指定客户端信息的数据来源
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * oauth2 主配置信息，用来配置授权、授权码及 token 令牌服务
     * 默认：
     *  认证端点：/oauth/authorize --> org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
     *  访问令牌端点：/oauth/token --> org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
     *  用户确认授权提交端点：/oauth/confirm_access --> org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint
     *  授权服务错误信息端点：/oauth/error --> org.springframework.security.oauth2.provider.endpoint.WhitelabelErrorEndpoint
     *  资源服务访问令牌解析端点：/oauth/check_token --> org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint
     *  使用 JWT 令牌时的公有密钥端点：/oauth/token_key --> org.springframework.security.oauth2.provider.endpoint.TokenKeyEndpoint
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 密码模式所需的认证管理对象
                .authorizationCodeServices(jdbcAuthorizationCodeServices()) // 授权码模式数据来源
                .approvalStore(jdbcApprovalStore()) // 授权信息保存策略
                .tokenStore(jwtTokenStore()) // token 持久化策略
                .accessTokenConverter(jwtAccessTokenConverter()) // 自定义 token 解析工具
                .tokenEnhancer(tokenEnhancerChain()) // 令牌增强
                .exceptionTranslator(new CustomWebResponseExceptionTranslator()) // 自定义异常处理
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); // 允许 GET、POST 请求 /oauth/token 端点
    }

    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 密码模式所需的认证管理对象
                .authorizationCodeServices(jdbcAuthorizationCodeServices()) // 授权码模式数据来源
                .approvalStore(jdbcApprovalStore()) // 授权信息保存策略
                .tokenServices(authorizationServerTokenServices()) // 令牌服务
                .exceptionTranslator(new CustomWebResponseExceptionTranslator()) // 自定义异常处理
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); // 允许 GET、POST 请求 /oauth/token 端点
    }*/

    /**
     * 令牌服务
     */
    /*@Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 令牌默认有效期 2 小时
        tokenServices.setAccessTokenValiditySeconds(7200);
        tokenServices.setAuthenticationManager(authenticationManager);
        tokenServices.setClientDetailsService(jdbcClientDetailsService());
        // 刷新令牌默认有效期 3 天
        tokenServices.setRefreshTokenValiditySeconds(259200);
        // 支持令牌刷新
        tokenServices.setSupportRefreshToken(true);
        // 令牌增强策略
        tokenServices.setTokenEnhancer(tokenEnhancerChain());
        // 令牌存储策略
        tokenServices.setTokenStore(jwtTokenStore());
        return tokenServices;
    }*/

}