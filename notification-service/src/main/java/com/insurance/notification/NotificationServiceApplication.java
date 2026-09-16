package com.insurance.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for Notification Service.
 *
 * Later this service will:
 *
 * 1. Consume POLICY_ACTIVATED events from RabbitMQ.
 * 2. Process notifications.
 * 3. Implement retry handling.
 * 4. Handle failed messages.
 * 5. Demonstrate idempotency.
 */
@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				NotificationServiceApplication.class,
				args
		);

	}
}