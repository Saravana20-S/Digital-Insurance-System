package com.insurance.batch.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RenewalEvent {

    private Long policyId;

    private Long customerId;

    private BigDecimal premiumAmount;

    private LocalDate dueDate;

    private String message;
}