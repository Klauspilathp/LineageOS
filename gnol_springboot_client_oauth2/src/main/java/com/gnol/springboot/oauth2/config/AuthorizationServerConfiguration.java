package com.gnol.springboot.oauth2.config;

import java.security.KeyPair;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @Title: AuthorizationServerConfiguration
 * @Package: com.gnol.springboot.oauth2.config
 * @author: 吴佳隆
 * @date: 2020年7月21日 下午6:51:30
 * @Description: oauth2 授权服务器策略
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;
    /**
     * SHA1 的 PasswordEncoder 加密实现类
     */
    @Resource(name = "sha1PasswordEncoder")
    private PasswordEncoder passwordEncoder;
    /**
     * 认证管理对象
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 客户端信息实现
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * token 持久化策略
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 授权信息保存策略
     */
    @Bean
    public ApprovalStore jdbcApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * JWT 编码的令牌值和 OAuth2 身份验证之间进行转换工具
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        /*converter.setSigningKey("secret");*/
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "oauth2".toCharArray())
                .getKeyPair("oauth2");
        converter.setKeyPair(keyPair);
        return converter;
    }

    /**
     * 授权码模式数据来源
     */
    @Bean
    public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 授权服务器安全策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder).tokenKeyAccess("permitAll()") // 所有客户端都能请求 /oauth/token_key 端点
                .checkTokenAccess("isAuthenticated()") // 已验证的客户端才能请求 /oauth/check_token 端点
                .allowFormAuthenticationForClients();
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
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 认证管理对象
                .approvalStore(jdbcApprovalStore()) // 授权信息保存策略
                .accessTokenConverter(jwtAccessTokenConverter()) // JWT 编码的令牌值和 OAuth2 身份验证之间进行转换工具
                .authorizationCodeServices(jdbcAuthorizationCodeServices()) // 授权码模式数据来源
                .tokenStore(jdbcTokenStore()); // token 持久化策略
    }

}