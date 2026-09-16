package com.insurance.notification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyActivatedEvent {

    private Long policyId;

    private Long customerId;

    private Long planId;

    private String planName;

    private String message;
}