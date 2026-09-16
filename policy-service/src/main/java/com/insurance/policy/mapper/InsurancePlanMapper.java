package com.insurance.policy.mapper;

import com.insurance.policy.dto.plan.InsurancePlanResponse;
import com.insurance.policy.entity.InsurancePlan;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting InsurancePlan entity to response DTO.
 */
@Component
public class InsurancePlanMapper {

    /**
     * Convert entity to response DTO.
     */
    public InsurancePlanResponse toResponse(
            InsurancePlan plan) {

        return InsurancePlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .description(plan.getDescription())
                .coverageAmount(plan.getCoverageAmount())
                .basePremium(plan.getBasePremium())
                .durationYears(plan.getDurationYears())
                .active(plan.getActive())
                .createdAt(plan.getCreatedAt())
                .build();
    }
}