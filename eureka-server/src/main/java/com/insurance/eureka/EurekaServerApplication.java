package com.insurance.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main class for the Eureka Server.
 *
 * Eureka Server acts as a service registry.
 *
 * All microservices such as:
 * - auth-service
 * - policy-service
 * - notification-service
 * - batch-service
 *
 * will register themselves with Eureka.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				EurekaServerApplication.class,
				args
		);
	}
}