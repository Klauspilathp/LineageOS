package com.gnol.springboot.gateway.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.core.enums.HttpStatus;
import com.gnol.plugins.core.exception.AuthRuntimeException;
import com.gnol.plugins.core.exception.GnolException;
import com.gnol.plugins.core.exception.GnolRuntimeException;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @Title: GnolZuulErrorAttributes
 * @Package: com.gnol.springboot.gateway.config
 * @author: 吴佳隆
 * @date: 2020年6月29日 上午11:15:17
 * @Description: 错误信息重写
 */
@Component
public class GnolZuulErrorAttributes extends DefaultErrorAttributes {
    private static final Logger logger = LoggerFactory.getLogger(GnolZuulErrorAttributes.class);

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
            } else if (cause instanceof GnolRuntimeException) {
                GnolRuntimeException exception = (GnolRuntimeException) cause;
                status = exception.getHs().getKey();
                message = exception.getMessage();
            } else if (cause instanceof GnolException) {
                GnolException exception = (GnolException) cause;
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