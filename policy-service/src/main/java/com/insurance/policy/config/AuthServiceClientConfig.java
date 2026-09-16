package com.insurance.policy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * RestClient configuration for auth-service.
 */
@Configuration
public class AuthServiceClientConfig {

    @Bean
    public RestClient restClient(
            @Value("${services.auth-service.url}") String authServiceUrl) {

        return RestClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }
}