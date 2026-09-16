package com.insurance.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class for Authentication Service.
 *
 * Responsibilities of this service will eventually include:
 *
 * 1. User registration
 * 2. Customer registration
 * 3. Login
 * 4. Password encryption
 * 5. JWT generation
 * 6. JWT validation
 * 7. Google OAuth2 / OIDC
 * 8. Role-based authorization
 */
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class AuthServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuthServiceApplication.class, args);

	}
}