package com.insurance.policy.entity;

/**
 * Represents the current status of an insurance policy.
 */
public enum PolicyStatus {

    /**
     * Policy created but first payment is not completed.
     */
    PAYMENT_PENDING,

    /**
     * Policy is currently active.
     */
    ACTIVE,

    /**
     * Policy period has ended.
     */
    EXPIRED,

    /**
     * Policy has been cancelled.
     */
    CANCELLED
}