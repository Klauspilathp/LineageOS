package com.d7c.springboot.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.d7c.oauth2.springboot.SecurityUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @Title: FeignRequestInterceptorConfiguration
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月28日 下午6:47:40
 * @Description: 使用 feign client 访问其他微服务时将 access_token 放入请求头或请求参数中。
 */
@Configuration
public class FeignRequestInterceptorConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                Authentication authentication = SecurityUtil.getAuthentication();
                if (authentication != null) {
                    if (authentication instanceof OAuth2Authentication) {
                        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                        template.header("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + details.getTokenValue());
                        // template.query(OAuth2AccessToken.ACCESS_TOKEN, access_token);
                    }
                }
            }
        };
        return requestInterceptor;
    }

}