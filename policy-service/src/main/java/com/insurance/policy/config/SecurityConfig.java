package com.insurance.policy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Admin endpoints
                        .requestMatchers("/api/admin/**")
                        .permitAll()        // change to hasRole("ADMIN") later

                        // Public plan APIs
                        .requestMatchers("/api/plans/**")
                        .permitAll()

                        .requestMatchers("/api/policies/**").permitAll()

                        .requestMatchers("/api/premiums/**").permitAll()
                        .requestMatchers("/api/payments/**").permitAll()
                        .requestMatchers("/api/renewals/**").permitAll()

                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}