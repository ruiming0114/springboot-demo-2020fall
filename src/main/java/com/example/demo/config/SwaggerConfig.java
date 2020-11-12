package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Default")
                .enable(true)
                .select()
                //指定扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                //path过滤
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("Liruiming","#","ruiming0114@buaa.edu.cn");
        return new ApiInfo("SpringBoot-Demo项目API文档", "使用该文档进行SpringBoot-Demo项目的前后端接口沟通", "1.0", "http://123.56.145.79:8088",
                contact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
    }
}
