package com.insurance.policy.controller;

import com.insurance.policy.dto.policy.PolicyRequest;
import com.insurance.policy.dto.policy.PolicyResponse;
import com.insurance.policy.service.PolicyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    /**
     * Create policy.
     */
    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(
            @Valid @RequestBody PolicyRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(policyService.createPolicy(request));
    }

    /**
     * Get ALL policies.
     */
    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getAllPolicies() {

        return ResponseEntity.ok(
                policyService.getAllPolicies()
        );
    }

    /**
     * Get policy by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                policyService.getPolicyById(id)
        );
    }

    /**
     * Get policies by customer ID.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PolicyResponse>> getPoliciesByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                policyService.getPoliciesByCustomer(customerId)
        );
    }
}