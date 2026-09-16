package com.insurance.policy.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Payment entity.
 */
class PaymentTest {

    @Test
    void shouldCreateSuccessfulPayment() {

        // Create successful payment.
        Payment payment = Payment.builder()
                .amount(
                        new BigDecimal("12000")
                )
                .status(
                        PaymentStatus.SUCCESS
                )
                .transactionReference(
                        "TXN-10001"
                )
                .build();

        // Verify object.
        assertNotNull(payment);

        assertEquals(
                new BigDecimal("12000"),
                payment.getAmount()
        );

        assertEquals(
                PaymentStatus.SUCCESS,
                payment.getStatus()
        );

        assertEquals(
                "TXN-10001",
                payment.getTransactionReference()
        );
    }

    @Test
    void shouldCreatePendingPayment() {

        Payment payment = Payment.builder()
                .amount(
                        new BigDecimal("12000")
                )
                .status(
                        PaymentStatus.PENDING
                )
                .build();

        assertNotNull(payment);

        assertEquals(
                PaymentStatus.PENDING,
                payment.getStatus()
        );
    }
}