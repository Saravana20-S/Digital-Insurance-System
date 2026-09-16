package com.insurance.policy.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InsurancePlan entity.
 *
 * No Spring.
 * No database.
 * No Mockito.
 */
class InsurancePlanTest {

    @Test
    void shouldCreateInsurancePlan() {

        // Create insurance plan.
        InsurancePlan plan = InsurancePlan.builder()
                .name("Secure Life Plan")
                .description("Basic life insurance plan")
                .coverageAmount(
                        new BigDecimal("1000000")
                )
                .basePremium(
                        new BigDecimal("12000")
                )
                .durationYears(20)
                .active(true)
                .build();

        // Verify object.
        assertNotNull(plan);

        assertEquals(
                "Secure Life Plan",
                plan.getName()
        );

        assertEquals(
                "Basic life insurance plan",
                plan.getDescription()
        );

        assertEquals(
                new BigDecimal("1000000"),
                plan.getCoverageAmount()
        );

        assertEquals(
                new BigDecimal("12000"),
                plan.getBasePremium()
        );

        assertEquals(
                20,
                plan.getDurationYears()
        );

        assertTrue(plan.getActive());
    }
}