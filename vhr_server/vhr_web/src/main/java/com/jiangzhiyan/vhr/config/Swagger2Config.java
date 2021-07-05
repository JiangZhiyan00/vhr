package com.jiangzhiyan.vhr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2 //开启Swagger2
public class Swagger2Config {

    /**
     * 配置扫描的包
     */
    @Bean
    public Docket createRestApi(){
        //文档类型:swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jiangzhiyan.vhr.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("微人事接口文档")
                .description("vhr接口文档")
                .contact(new Contact("JiangZhiyan","http://localhost:8081/doc.html","jaemusic@foxmail.com"))
                .version("1.0.0")
                .build();
    }
}
