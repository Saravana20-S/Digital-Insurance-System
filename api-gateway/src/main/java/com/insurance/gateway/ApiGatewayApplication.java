package com.insurance.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the API Gateway.
 *
 * The API Gateway is the single entry point for clients.
 *
 * Example:
 *
 * Client
 *   |
 *   v
 * API Gateway :8080
 *   |
 *   +----> Auth Service :8081
 *   |
 *   +----> Policy Service :8082
 *   |
 *   +----> Batch Service :8084
 */
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiGatewayApplication.class, args);

	}
}