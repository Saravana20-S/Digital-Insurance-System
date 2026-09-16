package com.insurance.policy.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for Insurance Plan.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePlanResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal coverageAmount;

    private BigDecimal basePremium;

    private Integer durationYears;

    private Boolean active;

    private LocalDateTime createdAt;
}