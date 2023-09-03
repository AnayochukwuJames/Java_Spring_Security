package com.james.springsecurity.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

import static java.awt.SystemColor.info;

public class OpenAPIConfig {
    @Bean
    public OpenAPI config(){
        return new OpenAPI().info(
                new Info().title("Spring Security")
                        .version("V1.0")
                        .description("Security for Rest APIs.... and MVCs")
        );
    }
}
