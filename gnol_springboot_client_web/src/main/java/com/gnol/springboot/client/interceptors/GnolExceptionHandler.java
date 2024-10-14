package com.gnol.springboot.client.interceptors;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.enums.HttpStatus;
import com.gnol.plugins.core.exception.GnolException;
import com.gnol.plugins.core.exception.GnolRuntimeException;

/**
 * @Title: GnolExceptionHandler
 * @Package: com.gnol.springboot.client.interceptors
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:27:00
 * @Description: 异常处理器
 */
@RestControllerAdvice
public class GnolExceptionHandler {

    @ExceptionHandler(GnolException.class)
    public PageResult handlerGnolException(GnolException e) {
        return PageResult.build(e.getHs());
    }

    @ExceptionHandler(GnolRuntimeException.class)
    public PageResult handlerGnolRuntimeException(GnolRuntimeException e) {
        return PageResult.build(e.getHs());
    }

    @ExceptionHandler(Exception.class)
    public PageResult handlerException(Exception e) {
        return PageResult.build(HttpStatus.HS_500.getKey(), e.getMessage());
    }

}