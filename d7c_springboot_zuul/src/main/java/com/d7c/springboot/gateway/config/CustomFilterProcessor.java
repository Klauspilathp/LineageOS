package com.d7c.springboot.gateway.config;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @Title: CustomFilterProcessor
 * @Package: com.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2021年1月4日 下午5:50:02
 * @Description: 自定义 {@link com.netflix.zuul.FilterProcessor}，用于向请求容器中添加一些过滤器处理信息。
 */
public class CustomFilterProcessor extends FilterProcessor {

    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
        try {
            return super.processZuulFilter(filter);
        } catch (ZuulException e) {
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.set("failed.exception", e);
            ctx.set("failed.filter", filter);
            throw e;
        }
    }

}