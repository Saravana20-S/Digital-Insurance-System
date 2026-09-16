package com.insurance.policy.controller;

import com.insurance.policy.jms.RenewalJmsProducer;
import com.insurance.policy.messaging.RenewalEvent;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/renewals")
@RequiredArgsConstructor
public class RenewalController {

    private final RenewalJmsProducer renewalJmsProducer;

    @PostMapping("/send")
    public ResponseEntity<String> sendRenewal(
            @RequestParam Long policyId,
            @RequestParam Long customerId,
            @RequestParam BigDecimal amount) {

        RenewalEvent event =
                RenewalEvent.builder()
                        .policyId(policyId)
                        .customerId(customerId)
                        .premiumAmount(amount)
                        .dueDate(LocalDate.now().plusDays(30))
                        .message(
                                "Insurance policy renewal request received."
                        )
                        .build();

        renewalJmsProducer.sendRenewal(event);

        return ResponseEntity.ok(
                "Renewal request sent successfully"
        );
    }
}