package com.gnol.springboot.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: Oauth2Application
 * @Package: com.gnol.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月15日 上午9:52:39
 * @Description: 资源授权服务器启动类
 */
@SpringBootApplication(scanBasePackages = {"com.gnol.springboot"})
@EnableDiscoveryClient // eureka 客户端
@EnableFeignClients(basePackages = {"com.gnol.springboot"}) // 启用 Fegin
@EnableCircuitBreaker // 启用 hystrix
@MapperScan(basePackages = {"com.gnol.springboot.common.daos", "com.gnol.springboot.client.daos"})
public class Oauth2Application {

    /**
     * jdbc 储存策略认证流程：
     *  1、sys_user 表系统用户通过 com.gnol.springboot.client.config.WebSecurityConfiguration 配置中的 
     *      com.gnol.springboot.client.config.UserDetailsServiceImpl.loadUserByUsername(String) 服务去从库中加载系统用户；
     *  2、oauth_client_details 表客户端通过 com.gnol.springboot.client.config.JdbcAuthorizationServerConfiguration 配置中的 
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
     * jwt 对称加密存储策略认证流程：
     *  1、授权服务器和资源服务器配置相同的加密密钥；
     *  2、其余流程同 jdbc 储存策略认证流程。
     *  
     * jwt 非对称（密钥对）加密存储策略认证流程：
     *  1、使用 JDK 的 keytool 生成密钥对，示例：
     *      命令帮助：keytool -help
     *      生成密钥帮助：keytool -genkeypair -help
     *      生成密钥库文件：keytool -genkeypair -alias oauth2 -keyalg RSA -keypass oauth2keypass -keystore oauth2.jks -storepass oauth2storepass
     *          生成的 oauth2.jks 密钥库文件在当前目录。
     *      生成一个公钥：keytool -list -rfc -keystore oauth2.jks --storepass oauth2storepass | openssl x509 -inform pem -pubkey
     *          将打印出的公钥放到一个文件中，例如 oauth2-public.txt 中。注意生成公钥时 oauth2.jks 文件必须是授权服务器上的 oauth2.jks 文件。
     *      keytool -genkeypair -alias oauth2 -keyalg RSA -keypass oauth2keypass -keystore oauth2.keystore -storepass oauth2storepass
     *      keytool -list -rfc --keystore oauth2.keystore -storepass oauth2storepass | openssl x509 -inform pem -pubkey
     *  2、将 oauth2.jks 放到授权服务器的 resources 目录下，将 oauth2-public.txt 放到资源服务器的 resources 目录下；
     *  3、其余流程同 jdbc 储存策略认证流程。
     */
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }

}