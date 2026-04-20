package com.gnol.springboot.gateway.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.gateway.filters.PreAuthFilter;
import com.netflix.hystrix.exception.HystrixTimeoutException;

/**
 * @Title: GnolFallbackProvider
 * @Package: com.gnol.springboot.gateway.fallback
 * @author: 吴佳隆
 * @date: 2020年6月23日 上午8:36:32
 * @Description: zuul 利用 hystrix 实现微服务级别的降级容错与回退
 */
@Component
public class GnolFallbackProvider implements FallbackProvider {
    private static final Logger logger = LoggerFactory.getLogger(PreAuthFilter.class);

    /**
     * 指定要监听的微服务名称，* 和 null 表示监听所有微服务。
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        logger.info("GnolFallbackProvider.fallbackResponse route:{} 进行熔断降级", route);
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                if (cause instanceof HystrixTimeoutException) {
                    return HttpStatus.GATEWAY_TIMEOUT;
                } else {
                    return HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 响应体
             */
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(
                        PageResult.build(com.gnol.plugins.core.enums.HttpStatus.HS_500.getKey(), "服务不可用，请稍后再试。")));
            }

            /**
             * 请求头信息
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", StandardCharsets.UTF_8);
                headers.setContentType(mt);
                return headers;
            }
        };
    }

}