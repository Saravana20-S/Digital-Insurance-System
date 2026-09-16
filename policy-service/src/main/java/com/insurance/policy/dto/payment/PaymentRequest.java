package com.insurance.policy.dto.payment;

import com.insurance.policy.entity.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "Policy ID is required")
    private Long policyId;

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "1.0",
            message = "Payment amount must be greater than 0"
    )
    private BigDecimal amount;

    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;
}