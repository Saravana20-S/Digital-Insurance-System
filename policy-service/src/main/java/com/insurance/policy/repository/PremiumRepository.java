package com.insurance.policy.repository;

import com.insurance.policy.entity.Premium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Premium entity.
 */
public interface PremiumRepository
        extends JpaRepository<Premium, Long> {

    /**
     * Find all premiums belonging to a policy.
     */
    List<Premium> findByPolicyId(Long policyId);
}