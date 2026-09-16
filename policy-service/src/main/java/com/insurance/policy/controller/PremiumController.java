package com.insurance.policy.controller;

import com.insurance.policy.dto.premium.PremiumRequest;
import com.insurance.policy.dto.premium.PremiumResponse;
import com.insurance.policy.service.PremiumService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PremiumController {

    private final PremiumService premiumService;

    /**
     * GET /api/policies/{policyId}/premiums
     */
    @GetMapping("/api/policies/{policyId}/premiums")
    public ResponseEntity<List<PremiumResponse>> getPremiumsByPolicy(
            @PathVariable Long policyId) {

        return ResponseEntity.ok(
                premiumService.getPremiumsByPolicy(policyId)
        );
    }

    /**
     * GET /api/premiums/{id}
     */
    @GetMapping("/api/premiums/{id}")
    public ResponseEntity<PremiumResponse> getPremiumById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                premiumService.getPremiumById(id)
        );
    }


}