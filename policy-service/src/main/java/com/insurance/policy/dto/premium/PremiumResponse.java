package com.insurance.policy.dto.premium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for Premium.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumResponse {

    private Long id;

    private Long policyId;

    private BigDecimal amount;

    private LocalDate dueDate;

    private Boolean paid;

    private LocalDateTime createdAt;
}