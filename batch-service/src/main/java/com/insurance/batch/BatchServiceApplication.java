package com.insurance.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for Batch Service.
 *
 * This service will eventually handle:
 *
 * Excel file upload
 *       ↓
 * Spring Batch Job
 *       ↓
 * ItemReader
 *       ↓
 * ItemProcessor
 *       ↓
 * ItemWriter
 *       ↓
 * Customer creation
 */
@SpringBootApplication
public class BatchServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				BatchServiceApplication.class,
				args
		);

	}
}