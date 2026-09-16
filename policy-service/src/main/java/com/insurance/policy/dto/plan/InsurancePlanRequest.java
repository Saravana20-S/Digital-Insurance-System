package com.insurance.policy.dto.plan;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request DTO for creating and updating an insurance plan.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePlanRequest {

    @NotBlank(message = "Plan name is required")
    private String name;

    @NotBlank(message = "Plan description is required")
    private String description;

    @NotNull(message = "Coverage amount is required")
    @DecimalMin(value = "1.0", message = "Coverage amount must be greater than 0")
    private BigDecimal coverageAmount;

    @NotNull(message = "Base premium is required")
    @DecimalMin(value = "1.0", message = "Base premium must be greater than 0")
    private BigDecimal basePremium;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer durationYears;

    private Boolean active;
}