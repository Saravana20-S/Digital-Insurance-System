package com.insurance.policy.repository;

import com.insurance.policy.entity.InsurancePlan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for InsurancePlan entity.
 */
public interface InsurancePlanRepository
        extends JpaRepository<InsurancePlan, Long> {
}