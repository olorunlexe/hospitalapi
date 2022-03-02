package com.assessment.hospitalapi.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Map;


@Configuration
public class SwaggerConfig {
    @Bean
    public Docket wlVisaApi(TypeResolver resolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .additionalModels(resolver.resolve(java.util.Map.class));

    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Hospital Management API")
                .version("1.0")
                .license("Apache License Version 2.0")
                .build();
    }
}