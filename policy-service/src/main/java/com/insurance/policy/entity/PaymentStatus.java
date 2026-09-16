package com.insurance.policy.entity;

/**
 * Represents payment processing status.
 */
public enum PaymentStatus {

    /**
     * Payment created but not completed.
     */
    PENDING,

    /**
     * Payment successfully completed.
     */
    SUCCESS,

    /**
     * Payment failed.
     */
    FAILED
}