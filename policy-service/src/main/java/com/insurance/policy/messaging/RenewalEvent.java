package com.insurance.policy.messaging;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RenewalEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long policyId;
    private Long customerId;
    private BigDecimal premiumAmount;
    private LocalDate dueDate;
    private String message;
}