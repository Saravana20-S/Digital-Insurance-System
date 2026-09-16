package com.insurance.policy.repository;


import com.insurance.policy.entity.Policy;
import com.insurance.policy.entity.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Policy entity.
 */
public interface PolicyRepository
        extends JpaRepository<Policy, Long> {

    /**
     * Find all policies belonging to a customer.
     */
    List<Policy> findByCustomerId(Long customerId);

    /**
     * Find policies by customer and status.
     */
    List<Policy> findByCustomerIdAndStatus(
            Long customerId,
            PolicyStatus status
    );
}