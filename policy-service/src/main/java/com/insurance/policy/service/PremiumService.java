package com.insurance.policy.service;

import com.insurance.policy.dto.premium.PremiumResponse;
import com.insurance.policy.entity.Premium;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.repository.PremiumRepository;

import com.insurance.policy.dto.premium.PremiumRequest;
import com.insurance.policy.entity.InsurancePlan;
import com.insurance.policy.repository.InsurancePlanRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for Premium operations.
 */
@Service
@RequiredArgsConstructor
public class PremiumService {

    private final PremiumRepository premiumRepository;
    private final InsurancePlanRepository insurancePlanRepository;

    /**
     * Get premiums for a policy.
     */
    @Transactional(readOnly = true)
    public List<PremiumResponse> getPremiumsByPolicy(
            Long policyId) {

        List<Premium> premiums =
                premiumRepository.findByPolicyId(
                        policyId
                );

        if (premiums.isEmpty()) {

            throw new ResourceNotFoundException(
                    "No premiums found for policy id: "
                            + policyId
            );
        }

        return premiums.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Get premium by ID.
     */
    @Transactional(readOnly = true)
    public PremiumResponse getPremiumById(
            Long id) {

        Premium premium =
                premiumRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Premium not found with id: "
                                                + id
                                )
                        );

        return toResponse(premium);
    }

    /**
     * Convert entity to DTO.
     */
    private PremiumResponse toResponse(
            Premium premium) {

        return PremiumResponse.builder()
                .id(premium.getId())
                .policyId(
                        premium.getPolicy().getId()
                )
                .amount(premium.getAmount())
                .dueDate(premium.getDueDate())
                .paid(premium.getPaid())
                .createdAt(premium.getCreatedAt())
                .build();
    }


    /**
     * Calculate premium without saving it.
     */
    @Transactional(readOnly = true)
    public PremiumResponse calculatePremium(
            PremiumRequest request) {

        InsurancePlan plan = insurancePlanRepository
                .findById(request.getPlanId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Insurance plan not found with id: "
                                        + request.getPlanId()
                        )
                );

        BigDecimal multiplier;

        int age = request.getCustomerAge();

        if (age <= 25) {
            multiplier = new BigDecimal("1.20");
        } else if (age <= 40) {
            multiplier = BigDecimal.ONE;
        } else if (age <= 60) {
            multiplier = new BigDecimal("1.30");
        } else {
            multiplier = new BigDecimal("1.50");
        }

        BigDecimal calculatedAmount =
                plan.getBasePremium().multiply(multiplier);

        return PremiumResponse.builder()
                .id(null)
                .policyId(null)
                .amount(calculatedAmount)
                .dueDate(LocalDate.now().plusMonths(1))
                .paid(false)
                .createdAt(LocalDateTime.now())
                .build();
    }


}