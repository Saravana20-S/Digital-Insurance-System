package com.insurance.policy.dto.policy;

import com.insurance.policy.entity.PolicyStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for Policy.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long customerId;

    private Long insurancePlanId;

    private String insurancePlanName;

    private BigDecimal coverageAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    private PolicyStatus status;

    private BigDecimal annualPremium;

    private LocalDateTime createdAt;
}