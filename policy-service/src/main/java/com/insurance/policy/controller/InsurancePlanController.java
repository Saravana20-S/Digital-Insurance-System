package com.insurance.policy.controller;

import com.insurance.policy.dto.plan.InsurancePlanRequest;
import com.insurance.policy.dto.plan.InsurancePlanResponse;
import com.insurance.policy.dto.premium.PremiumResponse;
import com.insurance.policy.service.InsurancePlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.insurance.policy.dto.premium.PremiumRequest;
import com.insurance.policy.service.PremiumService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/plans")
@RequiredArgsConstructor
public class InsurancePlanController {

    private final InsurancePlanService insurancePlanService;
    private final PremiumService premiumService;

    @PostMapping
    public ResponseEntity<InsurancePlanResponse> createPlan(
            @Valid @RequestBody InsurancePlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(insurancePlanService.createPlan(request));
    }

    @GetMapping
    public ResponseEntity<List<InsurancePlanResponse>> getAllPlans() {
        return ResponseEntity.ok(insurancePlanService.getAllPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsurancePlanResponse> getPlanById(
            @PathVariable Long id) {
        return ResponseEntity.ok(insurancePlanService.getPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsurancePlanResponse> updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody InsurancePlanRequest request) {
        return ResponseEntity.ok(
                insurancePlanService.updatePlan(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        insurancePlanService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Calculate premium without creating a policy.
     */
    @PostMapping("/calculate-premium")
    public ResponseEntity<PremiumResponse> calculatePremium(
            @Valid @RequestBody PremiumRequest request) {

        return ResponseEntity.ok(
                premiumService.calculatePremium(request)
        );
    }


}