package com.gnol.springboot.client.config;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.gnol.plugins.tools.date.DateUtil;

/**
 * @Title: ConverterConfiguration
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 上午8:08:26
 * @Description: 数据转换器配置
 */
@Configuration
public class ConverterConfiguration {
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @PostConstruct
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if ((initializer != null ? initializer.getConversionService() : null) != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
        }
    }

    /**
     * 默认时间转化器
     */
    public class StringToDateConverter implements Converter<String, Date> {
        @Override
        public Date convert(String dateString) {
            try {
                return DateUtil.parseDate(dateString, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日",
                        "yyyy年MM月dd日 HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}