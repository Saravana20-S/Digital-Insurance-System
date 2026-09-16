package com.insurance.policy.repository;


import com.insurance.policy.entity.Payment;
import com.insurance.policy.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Payment entity.
 */
public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    /**
     * Find all payments for a policy.
     */
    List<Payment> findByPolicyId(Long policyId);

    /**
     * Find payments by policy and status.
     */
    List<Payment> findByPolicyIdAndStatus(
            Long policyId,
            PaymentStatus status
    );
}