package com.insurance.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI authOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Digital Life Insurance Authentication API")
                        .version("1.0")
                        .description(
                                "Authentication and Customer Management APIs"
                        ));
    }
}