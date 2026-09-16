package com.insurance.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Temporary health endpoint for Phase 1.
 *
 * RabbitMQ consumer will be added later.
 */
@RestController
public class NotificationHealthController {

    /**
     * GET /api/notification/health
     */
    @GetMapping("/api/notification/health")
    public String health() {

        return "Notification Service is running successfully";

    }
}