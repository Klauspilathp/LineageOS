package com.gnol.springboot.common.configurations.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
// @RestControllerAdvice
public class GnolExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public PageResult handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        e.printStackTrace();
        return PageResult.error(String.format("Missing Request Parameter: %s", e.getParameterName()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public PageResult handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        return PageResult.error(String.format("Method Argument Type Mismatch: %s", e.getName()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public PageResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return PageResult.error(String.format("%s:%s", error.getField(), error.getDefaultMessage()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(BindException.class)
    public PageResult handlerBindException(BindException e) {
        e.printStackTrace();
        FieldError error = e.getFieldError();
        return PageResult.error(String.format("%s:%s", error.getField(), error.getDefaultMessage()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public PageResult handlerConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        return PageResult.error(String.format("%s:%s", path, violation.getMessage()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public PageResult handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return PageResult.error(e.getMessage());
    }

    @ExceptionHandler(GnolRuntimeException.class)
    public PageResult handlerGnolRuntimeException(GnolRuntimeException e) {
        e.printStackTrace();
        return PageResult.build(e.getHs().getKey(), e.getMessage());
    }

    @ExceptionHandler(GnolException.class)
    public PageResult handlerGnolException(GnolException e) {
        e.printStackTrace();
        return PageResult.build(e.getHs().getKey(), e.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public PageResult handlerException(Exception e) {
        e.printStackTrace();
        return PageResult.build(HttpStatus.HS_500.getKey(), e.getMessage());
    }

}