package com.insurance.batch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Temporary health endpoint for Phase 1.
 *
 * Real Excel import APIs will be implemented
 * during the Spring Batch phase.
 */
@RestController
public class BatchHealthController {

    /**
     * GET /api/batch/health
     */
    @GetMapping("/api/batch/health")
    public String health() {

        return "Batch Service is running successfully";

    }
}