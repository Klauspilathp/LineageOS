package top.d7c.springboot.client.config;

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
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2019年7月29日 下午6:20:09
 * @Description: swagger 配置类。
 * http://localhost:8093/swagger-ui.html
 * http://localhost:8093/v2/api-docs
 */
@Configuration // 让 spring 来加载该类配置
@EnableSwagger2 // 启用 swagger2
// @EnableWebMvc // 启用 MVC，非 springboot 框架需要引入该注解
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                // .apis(RequestHandlerSelectors.basePackage("top.d7c.springboot.client.controllers")) // 扫描指定包中的 swagger 注解
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) // 扫描所有有注解的 api
                .paths(PathSelectors.any()) // 任何路径
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("吴佳隆", "https://gitee.com/d7c/d7c_springboot.git", "wjl5760610@126.com");
        return new ApiInfoBuilder().title("d7c 系统 API 接口文档") // 大标题 title
                .description("基础 RESTful 风格的接口文档") // 小标题
                .termsOfServiceUrl("127.0.0.1:8093") // 终端服务程序
                .contact(contact) // 作者信息
                .license("d7c 系统 springboot 版本") // 链接显示文字
                .licenseUrl("https://gitee.com/d7c/d7c_springboot.git") // 网站链接
                .version("0.0.1") // 版本
                .build();
    }

}