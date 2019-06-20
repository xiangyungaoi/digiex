package com.caetp.digiex.config;

import com.caetp.digiex.response.ResponseEnum;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2Doc
public class Swagger {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(Character.class, String.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.caetp.digiex.controller.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API 文档")
                .description(buildDescription())
                .termsOfServiceUrl("http://192.168.1.6:8092/")
                // 文档制作者信息
                .contact(new Contact("Andy", "http://www.caetp.com", "1216892247@qq.com"))
                // 文档版本信息
                .version("1.0")
                .build();
    }

    private String buildDescription() {
        StringBuffer sb = new StringBuffer("API 文档 \n");
        for (ResponseEnum e : ResponseEnum.values())
            sb.append(e.getStatusCode() + "|" + e.getStatusMsg() + "|" + e.getMsg() + "\n");
        return sb.toString();
    }
}
