package com.d7c.springboot.client.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.d7c.plugins.core.StringUtil;

/**
 * @Title: FlowableModelerPasswordEncoder
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午2:33:19
 * @Description: flowable 密码编码器。
 */
@Component
public class FlowableModelerPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            rawPassword = StringUtil.EMPTY;
        }
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }

}
