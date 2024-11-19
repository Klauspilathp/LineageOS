package com.gnol.springboot.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Title: SwaggerConfig
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:28:52
 * @Description: swagger 配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                // .apis(RequestHandlerSelectors.basePackage("com.gnol.springboot")) // 扫描指定包中的 swagger 注解
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) // 扫描所有有注解的 api
                .paths(PathSelectors.any()) // 任何路径
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("吴佳隆", "https://gitee.com/wujialong576/gnol_springboot.git",
                "wjl5760610@126.com");
        return new ApiInfoBuilder().title("gnol 系统 API 接口文档") // 大标题 title
                .description("基础 RESTful 风格的接口文档") // 小标题
                .termsOfServiceUrl("127.0.0.1:8080/gnol.xxx") // 终端服务程序
                .contact(contact) // 作者信息
                .license("gnol 系统 springboot 版本") // 链接显示文字
                .licenseUrl("https://gitee.com/wujialong576/gnol_springboot.git") // 网站链接
                .version("0.0.1") // 版本
                .build();
    }

}