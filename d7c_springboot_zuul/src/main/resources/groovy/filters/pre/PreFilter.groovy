package top.d7c.springboot.gateway.filters

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException

/**
 * @Title: PreFilter
 * @Package: top.d7c.springboot.gateway.filters
 * @author: 吴佳隆
 * @date: 2021年1月4日 下午5:28:09
 * @Description: groovy 语言编写的 pre 类型过滤器脚本。
 */
class PreFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(PreFilter.class)

    /**
     * 是否执行该过滤器，true 表示执行
     */
    @Override
    public boolean shouldFilter() {
        return true
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext()
        logger.info("Go to the PRE type filter for processing.[{}]", ctx.getRequest().getServletPath())
        ctx.setResponseBody("This is PreFilter Filter.")
        return null
    }

    /**
     * 过滤器类型
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE
    }

    /**
     * 过滤器的执行顺序
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2
    }

}