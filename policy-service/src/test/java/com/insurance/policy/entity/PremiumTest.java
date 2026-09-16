package com.insurance.policy.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Premium entity.
 */
class PremiumTest {

    @Test
    void shouldCreatePremium() {

        // Create Premium.
        Premium premium = Premium.builder()
                .amount(
                        new BigDecimal("12000")
                )
                .dueDate(
                        LocalDate.now().plusMonths(1)
                )
                .paid(false)
                .build();

        // Verify object.
        assertNotNull(premium);

        assertEquals(
                new BigDecimal("12000"),
                premium.getAmount()
        );

        assertNotNull(
                premium.getDueDate()
        );

        assertFalse(
                premium.getPaid()
        );
    }

    @Test
    void shouldMarkPremiumAsPaid() {

        Premium premium = Premium.builder()
                .amount(
                        new BigDecimal("12000")
                )
                .dueDate(
                        LocalDate.now()
                )
                .paid(true)
                .build();

        assertTrue(
                premium.getPaid()
        );
    }
}