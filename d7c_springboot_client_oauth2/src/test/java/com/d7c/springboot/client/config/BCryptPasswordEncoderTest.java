package com.d7c.springboot.client.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Title: BCryptPasswordEncoderTest
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月25日 下午6:47:40
 * @Description: BCryptPasswordEncoder 测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BCryptPasswordEncoderTest {
    /**
     * 密码加解密
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test1() {
        System.out.println(passwordEncoder.encode("000000"));
    }

}