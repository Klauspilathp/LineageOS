package com.gnol.springboot.common.configurations.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.enums.HttpStatus;

/**
 * @Title: GnolErrorController
 * @Package: com.gnol.springboot.common.configurations.exception
 * @author: 吴佳隆
 * @date: 2020年7月21日 下午12:24:11
 * @Description: 取代 org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController 默认异常处理
 */
@ConditionalOnProperty( // 存在对应配置信息时初始化该配置类
        prefix = "gnol.exception", // 配置前缀 gnol.exception
        name = "enabled", // 配置名称
        havingValue = "true", // 与配置值比较，如果相等则配置生效
        matchIfMissing = true // 默认配置值，即默认开启此配置
)
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GnolErrorController implements ErrorController {
    /**
     * 错误属性绑定
     */
    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    @ResponseBody
    public PageResult doHandleError(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributesData = errorAttributes.getErrorAttributes(webRequest, true);
        HttpStatus httpStatus = HttpStatus.forKey(errorAttributesData.remove("status"));
        if (httpStatus == null) {
            httpStatus = HttpStatus.HS_500;
        }
        return PageResult.build(httpStatus).setExtData(errorAttributesData);
    }

}