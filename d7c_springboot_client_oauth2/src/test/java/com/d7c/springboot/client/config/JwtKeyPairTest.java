package com.d7c.springboot.client.config;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

/**
 * @Title: JwtKeyPairTest
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月30日 上午8:20:22
 * @Description: Jwt KeyPair 测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtKeyPairTest {

    /**
     * 测试创建 jwt 令牌
     */
    @Test
    public void testCreateJwt() {
        // 密钥库文件路径
        ClassPathResource classPathResource = new ClassPathResource("oauth2.jks");
        // 密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,
                "oauth2storepass".toCharArray());
        // 密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("oauth2", "oauth2keypass".toCharArray());
        // 获取私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        // jwt 令牌的内容
        Map<String, String> body = new HashMap<>();
        body.put("name", "wujialong");
        String bodyStr = JSON.toJSONString(body);
        // 生成 jwt 令牌
        Jwt jwt = JwtHelper.encode(bodyStr, new RsaSigner(aPrivate));
        // 生成 jwt 令牌编码
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }

    /**
     * 校验 jwt 令牌
     */
    @Test
    public void testVerify() {
        // 公钥
        String publickey = "-----BEGIN PUBLIC KEY-----\r\n"
                + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAilTq7wIOHYVBoD04//G5\r\n"
                + "oNZEzlMi+qcvYTjkhIX9aR5eeYwc+VrLD1QF7qlczsWf+F12ainz8UItIUkNB40U\r\n"
                + "dEZ2p1dzPOfo2xKMsYvZiZHrvRJfLBWY+M5AALWDaDb6ReakDSXsoDjkc5f1Us+N\r\n"
                + "xncYppRkwgI8Kd+vK5aDWEPbFFfzCIEwHeHYVMw4njebpJ5G3YMTfv92BiEi/pD4\r\n"
                + "3YZCipruDIz48PEI9+MNhXZs7/BnANrIf4N6XqqFCRi+ttPoL/UPsjrjwVnnJa2V\r\n"
                + "uwmm+lZkFyY0V3bQbqnlNl500ZM+Wc0mFBA6fg1Fjt1+e5ptMWzGlXEnNiJJEXnZ\r\n" 
                + "2QIDAQAB\r\n"
                + "-----END PUBLIC KEY-----";
        // jwt 令牌
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoid3VqaWFsb25nIn0.E35xlPT46GaufgxErmmMEzXssvKksQqPNzPHgzb6pN9ZEAieIsJozkfHiGOgSHx1nKbWY0fX8BLlJRkkHOiEMdN4BkHl5tBWGtuHjqEwwb8TJxWlNL-8L1LbB2qiCkHilO9GCwmAIPRilHbD_IDYG5uFzf-7LgJBPhYyOyM8xkz48te-eTU9d8UAAD0vm8XTvf6NPdmLzRvQBBuphDGKXBwqhztxdHXilIAQK8PQvqUR9hB9W90rhA78-GYElkjZBHDFiTaWQO3IJCKAvmus_d-ZjPcX-1QdTHyBQv__2Xmevxb_oY34p8Wx5f6yYXkOFpPd-HT2b19A7Yw0SS5HhA";
        // 校验 jwt 令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        // 拿到 jwt 令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }

}