package com.gnol.springboot.client.interceptors;

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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.enums.HttpStatus;
import com.gnol.plugins.core.exception.GnolException;
import com.gnol.plugins.core.exception.GnolRuntimeException;

/**
 * @Title: GnolExceptionHandler
 * @Package: com.gnol.springboot.client.interceptors
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:27:00
 * @Description: 统一异常处理
 */
// @RestControllerAdvice
@ControllerAdvice
public class GnolExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public PageResult handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return PageResult.error(String.format("Missing Request Parameter: %s", e.getParameterName()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public PageResult handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return PageResult.error(String.format("Method Argument Type Mismatch: %s", e.getName()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public PageResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return PageResult.error(String.format("%s:%s", error.getField(), error.getDefaultMessage()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public PageResult handlerBindException(BindException e) {
        FieldError error = e.getFieldError();
        return PageResult.error(String.format("%s:%s", error.getField(), error.getDefaultMessage()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public PageResult handlerConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        return PageResult.error(String.format("%s:%s", path, violation.getMessage()));
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public PageResult handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return PageResult.error(e.getMessage());
    }

    @ExceptionHandler(GnolRuntimeException.class)
    @ResponseBody
    public PageResult handlerGnolRuntimeException(GnolRuntimeException e) {
        return PageResult.build(e.getHs());
    }

    @ExceptionHandler(GnolException.class)
    public ModelAndView handlerGnolException(GnolException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", e.toString());
        mv.setViewName("error");
        return mv;

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PageResult handlerException(Exception e) {
        return PageResult.build(HttpStatus.HS_500.getKey(), e.getMessage());
    }

}