package com.gnol.springboot.client.interceptors;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
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
import com.gnol.plugins.core.exception.GnolRuntimeException;

/**
 * @Title: GnolExceptionHandler
 * @Package: com.gnol.springboot.client.interceptors
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:27:00
 * @Description: 统一异常处理
 */
@ControllerAdvice
public class GnolExceptionHandler {
    
    @ExceptionHandler(GnolRuntimeException.class)
    @ResponseBody
    public PageResult handlerGnolRuntimeException(GnolRuntimeException e) {
        return PageResult.build(e.getHs());
    }

   

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        model.put("exception", MethodArgumentTypeMismatchException.class.getName());
        model.put("message", String.format("Method Argument Type Mismatch: %s", e.getName()));
        return new ModelAndView("error/error", model);
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        model.put("exception", Exception.class.getName());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        model.put("message", String.format("%s:%s", error.getField(), error.getDefaultMessage()));
        return new ModelAndView("error/error", model);
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(BindException.class)
    public ModelAndView handlerBindException(BindException e) {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        model.put("exception", Exception.class.getName());
        FieldError error = e.getFieldError();
        model.put("message", String.format("%s:%s", error.getField(), error.getDefaultMessage()));
        return new ModelAndView("error/error", model);
    }

    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handlerConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        model.put("exception", Exception.class.getName());
        model.put("message", String.format("%s:%s", path, violation.getMessage()));
        return new ModelAndView("error/error", model);
    }

    /**
     * 
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        model.put("exception", MissingServletRequestParameterException.class.getName());
        model.put("message", String.format("Missing Request Parameter: %s", e.getParameterName()));
        return new ModelAndView("error/error", model);
    }
    
    

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception e) {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        if (e instanceof MissingServletRequestParameterException) { // 缺少参数
            model.put("exception", MissingServletRequestParameterException.class.getName());
            model.put("message", e.getMessage() == null ? e.toString() : e.getMessage());
        }
        
        model.put("exception", Exception.class.getName());
        model.put("message", e.getMessage() == null ? e.toString() : e.getMessage());
        return new ModelAndView("error/error", model);
    }

}