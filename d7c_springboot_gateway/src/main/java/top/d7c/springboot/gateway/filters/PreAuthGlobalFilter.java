package top.d7c.springboot.gateway.filters;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.springboot.common.constant.AuthConstant;

import reactor.core.publisher.Mono;

/**
 * @Title: PreAuthGlobalFilter
 * @Package: top.d7c.springboot.gateway.filters
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午12:58:06
 * @Description: gateway 认证前置过滤器
 */
@Component
public class PreAuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (StringUtil.startsWithAny(path, "/auth/", "/oauth2/oauth/")) {
            return chain.filter(exchange);
        }
        // 从请求中获取 token 参数
        String token = getTokenByRequest(request);
        // 如果为空，那么将返回 401
        if (StringUtil.isBlank(token)) {
            ServerHttpResponse response = exchange.getResponse();
            // 转换响应消息内容对象为字节
            byte[] bits = PageResult.build(top.d7c.plugins.core.enums.HttpStatus.HS_401.getKey(), "没有 token").toString()
                    .getBytes(StandardCharsets.UTF_8);
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

    // 从 ServerHttpRequest 中获取 token
    private String getTokenByRequest(ServerHttpRequest request) {
        String token = getTokenByHttpHeaders(request.getHeaders());
        if (token == null) {
            // 从请求参数中获取 token
            token = request.getQueryParams().getFirst(AuthConstant.TOKEN);
        }
        return token;
    }

    // 从 HttpHeaders 中获取 token
    private String getTokenByHttpHeaders(HttpHeaders headers) {
        if (headers == null) {
            return null;
        }
        // 获取 Authorization 请求头中的 token
        if (headers.containsKey(AuthConstant.AUTH_HEADER)) {
            // List<String> tokens = headers.get(AuthConstant.AUTH_HEADER);
            String token = headers.getFirst(AuthConstant.AUTH_HEADER);
            return token == null ? null : token.substring("Bearer ".length());
        }
        // 获取 token 请求头中的 token
        if (headers.containsKey(AuthConstant.TOKEN)) {
            // List<String> tokens = headers.get(AuthConstant.TOKEN);
            return headers.getFirst(AuthConstant.TOKEN);
        }
        return null;
    }

}