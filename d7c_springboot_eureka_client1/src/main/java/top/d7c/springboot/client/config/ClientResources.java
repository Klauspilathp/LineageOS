package top.d7c.springboot.client.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * @Title: ClientResources
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月31日 上午9:30:26
 * @Description: 客户端资源
 */
public class ClientResources {
    /**
     * 资源服务配置
     */
    @NestedConfigurationProperty
    private ResourceServerProperties resourceServer = new ResourceServerProperties();
    /**
     * 授权码模式
     */
    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails codeResourceDetails = new AuthorizationCodeResourceDetails();
    /**
     * 客户端模式
     */
    @NestedConfigurationProperty
    private ClientCredentialsResourceDetails credentialsResourceDetails = new ClientCredentialsResourceDetails();

    public ResourceServerProperties getResourceServer() {
        return resourceServer;
    }

    public AuthorizationCodeResourceDetails getCodeResourceDetails() {
        return codeResourceDetails;
    }

    public ClientCredentialsResourceDetails getCredentialsResourceDetails() {
        return credentialsResourceDetails;
    }

}