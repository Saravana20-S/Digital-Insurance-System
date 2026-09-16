package com.insurance.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class for Policy Service.
 *
 * This service will eventually manage:
 *
 * 1. Insurance Plans
 * 2. Premium Calculation
 * 3. Policies
 * 4. Premium records
 * 5. Payments
 * 6. Policy renewal
 * 7. Redis caching
 * 8. RabbitMQ policy events
 * 9. JMS renewal processing
 */
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class PolicyServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(PolicyServiceApplication.class, args);

	}
}