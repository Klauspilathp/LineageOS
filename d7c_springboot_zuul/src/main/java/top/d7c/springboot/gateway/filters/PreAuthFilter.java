package top.d7c.springboot.gateway.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.core.exception.AuthRuntimeException;
import top.d7c.springboot.auth.feigns.AuthFeignClient;
import top.d7c.springboot.common.constant.AuthConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @Title: PreAuthFilter
 * @Package: top.d7c.springboot.gateway.filters
 * @author: 吴佳隆
 * @date: 2020年6月22日 下午8:15:56
 * @Description: zuul 认证前置过滤器，Spring Cloud 为 zuul 在 spring-cloud-netflix-core 包中默认启用了一些过滤器。
 * 如果要禁用过滤器，可以在属性文件中配置 zuul.<SimpleClassName>.<FilterType>.disable=true，例如 zuul.PreRequestLogFilter.pre.disable=true
 */
@Component
public class PreAuthFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(PreAuthFilter.class);
    @Autowired
    private AuthFeignClient authFeignClient;

    /**
     * 是否执行该过滤器，true 表示执行
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (StringUtil.startsWith(request.getServletPath(), "/auth/")) {
            return false;
        }
        return true;
    }

    /**
     * 过滤器的执行逻辑
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = getTokenByRequest(request);
        if (StringUtil.isBlank(token)) {
            throw new AuthRuntimeException("非法请求！");
        }
        String servlet_path = request.getServletPath();
        PageResult result = authFeignClient.validate(token, servlet_path);
        if (!result.isOk()) {
            logger.debug("{} 路径的接口被 token {} 的用户非法访问...", servlet_path, token);
            /*ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.HS_401.getKey());
            return null;*/
            throw new AuthRuntimeException(result.getMessage());
        }
        // return 值没有意义，zuul 框架没有使用
        return null;
    }

    /**
     * @Title: getTokenByRequest
     * @author: 吴佳隆
     * @data: 2020年6月28日 上午11:51:02
     * @Description: 获取请求中的 token
     * @param request   javax.servlet.http.HttpServletRequest 对象
     * @return String   token 字符串
     */
    private String getTokenByRequest(HttpServletRequest request) {
        String token = request.getHeader(AuthConstant.AUTH_HEADER);
        if (StringUtil.isBlank(token)) {
            // 获取 token 请求头中的 token
            token = request.getHeader(AuthConstant.TOKEN);
            if (StringUtil.isBlank(token)) {
                // 从参数中获取 token
                token = request.getParameter(AuthConstant.TOKEN);
            }
        } else {
            token = token.substring("Bearer ".length());
        }
        return token;
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
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

}