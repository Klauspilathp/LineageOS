package com.gnol.springboot.gateway.filters;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;

import reactor.core.publisher.Mono;

/**
 * @Title: PreAuthGlobalFilter
 * @Package: com.gnol.springboot.gateway.filters
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午12:58:06
 * @Description: gateway 认证前置过滤器
 */
// @Component
public class PreAuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String value = exchange.getRequest().getPath().value();
        if (StringUtil.startsWith(value, "/auth/")) {

        }
        ServerHttpResponse response = exchange.getResponse();
        // 从请求中获取 token 参数
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        // 如果为空，那么将返回 401
        if (token == null || token.isEmpty()) {
            // 转换响应消息内容对象为字节
            byte[] bits = PageResult.build(com.gnol.plugins.core.enums.HttpStatus.HS_401.getKey(), "没有 token")
                    .toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            // 设置响应对象状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 设置响应对象内容并且指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            // 返回响应对象
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

}