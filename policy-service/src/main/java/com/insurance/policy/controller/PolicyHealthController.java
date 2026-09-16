package com.insurance.policy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Temporary health/test endpoint for Phase 1.
 *
 * Actual PolicyController will be implemented in Phase 4.
 */
@RestController
public class PolicyHealthController {

    /**
     * GET /api/policy/health
     */
    @GetMapping("/api/policy/health")
    public String health() {

        return "Policy Service is running successfully";

    }
}