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
import com.gnol.plugins.core.enums.HttpStatus;
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
        return PageResult.build(e.getHs().getKey(),
                e.getMessage() == null ? HttpStatus.getValue(e.getHs().getKey()) : e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception e) {
        e.printStackTrace();
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("timestamp", new Date());
        if (e instanceof MissingServletRequestParameterException) { // 缺少参数
            model.put("exception", MissingServletRequestParameterException.class.getName());
            model.put("message", String.format("Missing Request Parameter: %s",
                    ((MissingServletRequestParameterException) e).getParameterName()));
        } else if (e instanceof MethodArgumentTypeMismatchException) { // 参数校验错误异常
            model.put("exception", MethodArgumentTypeMismatchException.class.getName());
            model.put("message", String.format("Method Argument Type Mismatch: %s",
                    ((MethodArgumentTypeMismatchException) e).getName()));
        } else if (e instanceof MethodArgumentNotValidException) { // 参数校验错误异常
            model.put("exception", MethodArgumentNotValidException.class.getName());
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            FieldError error = result.getFieldError();
            model.put("message", String.format("%s:%s", error.getField(), error.getDefaultMessage()));
        } else if (e instanceof BindException) { // 参数校验错误异常
            model.put("exception", BindException.class.getName());
            FieldError error = ((BindException) e).getFieldError();
            model.put("message", String.format("%s:%s", error.getField(), error.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException) { // 参数校验错误异常
            model.put("exception", ConstraintViolationException.class.getName());
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
            ConstraintViolation<?> violation = violations.iterator().next();
            String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
            model.put("message", String.format("%s:%s", path, violation.getMessage()));
        } else {
            model.put("exception", e.getClass().getName());
            model.put("message", e.getMessage() == null ? e.toString() : e.getMessage());
        }
        return new ModelAndView("error/error", model);
    }

}