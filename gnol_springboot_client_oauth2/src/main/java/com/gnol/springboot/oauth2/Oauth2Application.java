package com.gnol.springboot.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: Oauth2Application
 * @Package: com.gnol.springboot.oauth2
 * @author: 吴佳隆
 * @date: 2020年6月15日 上午9:52:39
 * @Description: 资源授权服务器启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"com.gnol.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"com.gnol.springboot.common.daos", "com.gnol.springboot.oauth2.daos"})
public class Oauth2Application {

    /**
     * jdbc 储存策略认证流程：
     *  1、sys_user 表系统用户通过 com.gnol.springboot.oauth2.config.WebSecurityConfiguration 配置中的 
     *      com.gnol.springboot.oauth2.config.UserDetailsServiceImpl.loadUserByUsername(String) 服务去从库中加载系统用户；
     *  2、oauth_client_details 表客户端通过 com.gnol.springboot.oauth2.config.JdbcAuthorizationServerConfiguration 配置中的 
     *      org.springframework.security.oauth2.provider.client.JdbcClientDetailsService.loadClientByClientId(String) 服务
     *      去主库中加载客户端信息；
     *  3、数据库中插入 INSERT INTO `oauth2`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, 
     *      `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, 
     *      `additional_information`, `autoapprove`, `create_time`) VALUES ('client1', 'gnol-springboot-client-api', 
     *      '$2a$10$rJGklBeto2hvmhdWnHp85etyZPpWRTOSIK/aNWfmXVsKNcbX.Dsfq', 'read,write', 
     *      'client_credentials,implicit,authorization_code,refresh_token,password', 'http://www.baidu.com', NULL, NULL, NULL, 
     *      NULL, 'false', '2020-07-26 11:19:03');
     *  4、授权码模式示例：
     *      浏览器请求 http://127.0.0.1:9101/oauth/authorize?response_type=code&client_id=client1 接口；
     *      输入 sys_user 表系统用户名和密码认证登录，首次会跳转到授权确认页面，确认后跳转到授权回调页面；
     *      从浏览器地址栏获取授权码；
     *      使用 Postman 以 form-data 表单提交方式发送 POST 请求提交
     *          grant_type:authorization_code（授权码模式）
     *          client_id:client1（客户端标识）
     *          client_secret:000000（客户端密钥）
     *          code:Y8yBUR（授权码）
     *      数据到 127.0.0.1:9101/oauth/token 接口获取授权 token；
     *      使用 GET 请求 url 后加 access_token=token 值、或 POST 请求 form-data 方式提交 access_token=token 值、或加入 OAuth2 认证请求头就可以访问资源服务器
     *      http://127.0.0.1:8091 上的资源了。
     *  5、简化模式示例：
     *      浏览器请求 http://127.0.0.1:9101/oauth/authorize?response_type=token&client_id=client1 接口；
     *      输入 sys_user 表系统用户名和密码认证登录，从浏览器地址栏获取 access_token；
     *  6、密码模式示例：
     *      直接使用 Postman 以 form-data 表单提交方式发送 POST 请求提交
     *          grant_type:password（密码模式）
     *          username:wujialong（系统用户名）
     *          password:000000（系统用户密码）
     *          client_id:client1（客户端标识）
     *          client_secret:000000（客户端密钥）
     *      数据到 127.0.0.1:9101/oauth/token 接口获取授权 token；
     *  7、客户端模式示例：
     *      直接使用 Postman 以 form-data 表单提交方式发送 POST 请求提交
     *          grant_type:client_credentials（客户端模式）
     *          client_id:client1（客户端标识）
     *          client_secret:000000（客户端密钥）
     *      数据到 127.0.0.1:9101/oauth/token 接口获取授权 token。
     *  
     * jwt 存储策略认证流程：
     *  1、
     *  2、
     *  3、
     *  4、
     */
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }

}