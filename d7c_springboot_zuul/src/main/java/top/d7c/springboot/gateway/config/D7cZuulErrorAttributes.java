package top.d7c.springboot.gateway.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.core.enums.HttpStatus;
import top.d7c.plugins.core.exception.AuthRuntimeException;
import top.d7c.plugins.core.exception.D7cException;
import top.d7c.plugins.core.exception.D7cRuntimeException;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @Title: D7cZuulErrorAttributes
 * @Package: top.d7c.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年6月29日 上午11:15:17
 * @Description: 错误信息重写
 */
@Component
public class D7cZuulErrorAttributes extends DefaultErrorAttributes {
    private static final Logger logger = LoggerFactory.getLogger(D7cZuulErrorAttributes.class);

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        int status; // 错误状态码
        String message; // 错误描述
        if (throwable instanceof ZuulException) {
            Throwable cause = ((ZuulException) throwable).getCause();
            if (cause instanceof AuthRuntimeException) {
                AuthRuntimeException exception = (AuthRuntimeException) cause;
                status = exception.getHs().getKey();
                message = exception.getMessage();
            } else if (cause instanceof D7cRuntimeException) {
                D7cRuntimeException exception = (D7cRuntimeException) cause;
                status = exception.getHs().getKey();
                message = exception.getMessage();
            } else if (cause instanceof D7cException) {
                D7cException exception = (D7cException) cause;
                status = exception.getHs().getKey();
                message = exception.getMessage();
            } else {
                status = HttpStatus.HS_500.getKey();
                message = cause.toString();
            }
        } else {
            Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
            logger.debug("原错误信息 {}...", errorAttributes.toString());
            status = errorAttributes.get("status") == null ? HttpStatus.HS_500.getKey()
                    : StringUtil.toInt(errorAttributes.get("status"));
            message = errorAttributes.get("message") == null ? HttpStatus.getValue(status)
                    : errorAttributes.get("message").toString();
        }
        return new HashMap<String, Object>() {
            private static final long serialVersionUID = -4100714660040208802L;
            {
                put("status", status);
                put("message", message);
            }
        };
    }

}