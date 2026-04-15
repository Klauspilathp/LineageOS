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
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        logger.debug("原错误信息 {}...", errorAttributes.toString());
        int status = errorAttributes.get("status") == null ? HttpStatus.HS_500.getKey()
                : StringUtil.toInt(errorAttributes.get("status"));
        String message = errorAttributes.get("message") == null ? HttpStatus.getValue(status)
                : errorAttributes.get("message").toString();
        return new HashMap<String, Object>() {
            private static final long serialVersionUID = -4100714660040208802L;
            {
                put("status", status);
                put("message", message);
            }
        };
    }

}