package com.gnol.springboot.common.configurations.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.enums.HttpStatus;
import com.gnol.plugins.core.exception.GnolException;
import com.gnol.plugins.core.exception.GnolRuntimeException;

/**
 * @Title: GnolExceptionHandler
 * @Package: com.gnol.springboot.common.configurations.exception
 * @author: 吴佳隆
 * @date: 2020年7月4日 下午5:53:06
 * @Description: 统一异常处理
 */
@ConditionalOnProperty( // 存在对应配置信息时初始化该配置类
        prefix = "gnol.exception", // 配置前缀 gnol.exception
        name = "enabled", // 配置名称
        havingValue = "true", // 与配置值比较，如果相等则配置生效
        matchIfMissing = false // 默认配置值，即默认开启此配置
)
@RestControllerAdvice
public class GnolExceptionHandler {

    @ExceptionHandler(GnolRuntimeException.class)
    public PageResult handlerGnolRuntimeException(GnolRuntimeException e) {
        return PageResult.build(e.getHs().getKey(),
                e.getMessage() == null ? HttpStatus.getValue(e.getHs().getKey()) : e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public PageResult handlerException(Exception e) {
        e.printStackTrace();
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        String message = null;
        int status = HttpStatus.HS_270.getKey();
        if (e instanceof MissingServletRequestParameterException) { // 缺少参数
            model.put("exception", MissingServletRequestParameterException.class.getName());
            message = String.format("Missing Request Parameter: %s",
                    ((MissingServletRequestParameterException) e).getParameterName());
        } else if (e instanceof MethodArgumentTypeMismatchException) { // 参数校验错误异常
            model.put("exception", MethodArgumentTypeMismatchException.class.getName());
            message = String.format("Method Argument Type Mismatch: %s",
                    ((MethodArgumentTypeMismatchException) e).getName());
        } else if (e instanceof MethodArgumentNotValidException) { // 参数校验错误异常
            model.put("exception", MethodArgumentNotValidException.class.getName());
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            FieldError error = result.getFieldError();
            message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        } else if (e instanceof BindException) { // 参数校验错误异常
            model.put("exception", BindException.class.getName());
            FieldError error = ((BindException) e).getFieldError();
            message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        } else if (e instanceof ConstraintViolationException) { // 参数校验错误异常
            model.put("exception", ConstraintViolationException.class.getName());
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
            ConstraintViolation<?> violation = violations.iterator().next();
            String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
            message = String.format("%s:%s", path, violation.getMessage());
        } else if (e instanceof GnolException) { // gnol 系统抛出的需要处理异常定义基类
            model.put("exception", e.getClass().getName());
            message = e.getMessage();
            status = ((GnolException) e).getHs().getKey();
        } else {
            model.put("exception", e.getClass().getName());
            message = e.getMessage() == null ? e.toString() : e.getMessage();
        }
        model.put("message", message);
        return PageResult.build(status, message).setExtData(model);
    }

}