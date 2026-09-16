package com.insurance.auth.entity;

/**
 * Represents the role of a user in the system.
 *
 * CUSTOMER:
 *     Normal insurance customer.
 *
 * ADMIN:
 *     Can manage insurance plans and administrative operations.
 *
 * EMPLOYEE:
 *     Insurance employee.
 */
public enum Role {

    CUSTOMER,

    ADMIN,

    EMPLOYEE
}