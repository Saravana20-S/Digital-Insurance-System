package com.insurance.policy.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Policy entity.
 */
class PolicyTest {

    @Test
    void shouldCreatePolicy() {

        // Create Insurance Plan.
        InsurancePlan plan = InsurancePlan.builder()
                .name("Secure Life Plan")
                .description("Life insurance plan")
                .coverageAmount(
                        new BigDecimal("1000000")
                )
                .basePremium(
                        new BigDecimal("12000")
                )
                .durationYears(20)
                .active(true)
                .build();

        // Create Policy.
        Policy policy = Policy.builder()
                .customerId(1L)
                .insurancePlan(plan)
                .startDate(LocalDate.now())
                .endDate(
                        LocalDate.now().plusYears(20)
                )
                .status(
                        PolicyStatus.PAYMENT_PENDING
                )
                .build();

        // Verify Policy.
        assertNotNull(policy);

        assertEquals(
                1L,
                policy.getCustomerId()
        );

        // Verify InsurancePlan relationship.
        assertNotNull(
                policy.getInsurancePlan()
        );

        assertEquals(
                plan,
                policy.getInsurancePlan()
        );

        // Verify dates.
        assertNotNull(
                policy.getStartDate()
        );

        assertNotNull(
                policy.getEndDate()
        );

        // Verify status.
        assertEquals(
                PolicyStatus.PAYMENT_PENDING,
                policy.getStatus()
        );
    }
}