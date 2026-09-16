package com.insurance.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple health/test controller for Phase 1.
 *
 * This controller is NOT the real authentication controller.
 *
 * Real authentication APIs will be implemented later.
 */
@RestController
public class AuthHealthController {

    /**
     * Test endpoint.
     *
     * GET /api/auth/health
     */
    @GetMapping("/api/auth/health")
    public String health() {

        return "Auth Service is running successfully";

    }
}