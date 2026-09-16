package com.insurance.policy.dto.payment;

import com.insurance.policy.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for Payment.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;

    private Long policyId;

    private BigDecimal amount;

    private PaymentStatus status;

    private String transactionReference;

    private LocalDateTime paymentDate;
}