package com.d7c.springboot.client.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Title: D7cProperties
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月14日 上午9:18:53
 * @Description: d7c 系统自定义属性
 */
@ConditionalOnProperty( // 存在对应配置信息时初始化该配置类
        prefix = "d7c", // 配置前缀 d7c
        name = "enabled", // 配置名称
        havingValue = "true", // 与配置值比较，如果相等则配置生效
        matchIfMissing = true // 默认配置值，即默认开启此配置
)
@ConfigurationProperties(prefix = "d7c")
public class D7cProperties {
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 默认密码
     */
    private String defaultPassword;
    /**
     * 公钥字符串
     */
    private String publicKeyStr;
    /**
     * RSA 公钥对象
     */
    private RSAPublicKey rsaPublicKey;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getPublicKeyStr() {
        return publicKeyStr;
    }

    public void setPublicKeyStr(String publicKeyStr) {
        this.publicKeyStr = publicKeyStr;
    }

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    // 修饰一个非静态的 void() 方法，被 @PostConstruct 修饰的方法会在服务器加载 Servlet 的时候被服务器调用一次，类似于 Servlet 的 init() 方法。
    @PostConstruct
    public void loadPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.rsaPublicKey == null) {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        }
        if (D7cConstant.LOCAL_IP == null) { // 获取本地 ip 地址
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException var4) {
                var4.printStackTrace();
            }
            if (inet != null) {
                String ipAddress = inet.getHostAddress();
                if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
                D7cConstant.LOCAL_IP = ipAddress;
            }
        }
    }

}