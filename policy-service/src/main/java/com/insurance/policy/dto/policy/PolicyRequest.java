package com.insurance.policy.dto.policy;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Request DTO for creating a policy.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Insurance plan ID is required")
    private Long insurancePlanId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
}