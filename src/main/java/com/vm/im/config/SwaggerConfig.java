package com.vm.im.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


/**
 * Swagger
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // 设置默认TOKEN，方便测试
    private static final String TOKEN = "afad71e8a8f944f0afc4e79e9520d71d";

    @Value("${swagger.config.antPattern}")
    private String antPattern = "/**";

    @Bean
    public Docket api() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder uidPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("用户服务生成").defaultValue(TOKEN).modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        uidPar.name("userId").description("用户id").defaultValue(TOKEN).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        pars.add(uidPar.build());
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vm.im.controller"))//扫描包的路径
                .paths(PathSelectors.ant(antPattern))
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error")).build(), new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build()));
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "VM-IM API",
                "VM 聊天室 API ",
                "1.0",
                "",
                new Contact("Zhang Qi", "http://www.baidu.com", "18865662499@163.com"),
                "点击联系开发者", "http://www.baidu.com", Collections.emptyList());
    }
    //   http://localhost:8180/swagger-ui.html
}
