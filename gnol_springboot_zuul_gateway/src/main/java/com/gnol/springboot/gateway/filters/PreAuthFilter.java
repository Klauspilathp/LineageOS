package com.gnol.springboot.gateway.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @Title: PreAuthFilter
 * @Package: com.gnol.springboot.gateway.filters
 * @author: 吴佳隆
 * @date: 2020年6月22日 下午8:15:56
 * @Description: zuul 认证前置过滤器，Spring Cloud 为 zuul 在 spring-cloud-netflix-core 包中默认启用了一些过滤器。
 * 如果要禁用过滤器，可以在属性文件中配置 zuul.<SimpleClassName>.<FilterType>.disable=true，例如 zuul.PreRequestLogFilter.pre.disable=true
 */
public class PreAuthFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(PreAuthFilter.class);

    /**
     * 是否执行该过滤器，true 表示执行
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的执行逻辑
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info(String.format("send %s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }

    /**
     * 过滤器类型
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器的执行顺序
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

}