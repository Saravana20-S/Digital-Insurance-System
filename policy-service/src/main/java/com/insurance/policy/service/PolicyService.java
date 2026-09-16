package com.insurance.policy.service;

import com.insurance.policy.dto.policy.PolicyRequest;
import com.insurance.policy.dto.policy.PolicyResponse;
import com.insurance.policy.entity.InsurancePlan;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.entity.PolicyStatus;
import com.insurance.policy.entity.Premium;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.repository.InsurancePlanRepository;
import com.insurance.policy.repository.PolicyRepository;
import com.insurance.policy.repository.PremiumRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Business logic for policy operations.
 *
 * Phase 5:
 * - Policy creation
 * - Customer verification
 * - Insurance plan verification
 * - Premium calculation
 *
 * Phase 8:
 * - Redis caching
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyService {

    private final PolicyRepository policyRepository;

    private final InsurancePlanRepository insurancePlanRepository;

    private final PremiumRepository premiumRepository;

    private final AuthCustomerClient authCustomerClient;


    /**
     * Create a new policy.
     *
     * New policies are initially PAYMENT_PENDING.
     *
     * The first premium is also created here.
     */
    @Transactional
    @CacheEvict(
            value = "allPolicies",
            allEntries = true
    )
    public PolicyResponse createPolicy(
            PolicyRequest request) {

        log.info(
                "Creating policy for customerId={}, planId={}",
                request.getCustomerId(),
                request.getInsurancePlanId()
        );

        /*
         * Verify customer through auth-service.
         */
        AuthCustomerClient.CustomerDetails customer =
                authCustomerClient.getCustomer(
                        request.getCustomerId()
                );

        /*
         * Verify insurance plan.
         */
        InsurancePlan plan =
                insurancePlanRepository.findById(
                                request.getInsurancePlanId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance plan not found with id: "
                                                + request.getInsurancePlanId()
                                )
                        );

        /*
         * Check whether plan is active.
         */
        if (!Boolean.TRUE.equals(plan.getActive())) {

            throw new IllegalStateException(
                    "Insurance plan is inactive"
            );
        }

        /*
         * Start date comes from request.
         */
        LocalDate startDate =
                request.getStartDate();

        /*
         * Calculate policy end date.
         */
        LocalDate endDate =
                startDate.plusYears(
                        plan.getDurationYears()
                );

        /*
         * Calculate illustrative annual premium.
         */
        BigDecimal annualPremium =
                calculatePremium(
                        plan,
                        customer.dateOfBirth()
                );

        /*
         * Create policy.
         */
        Policy policy =
                Policy.builder()
                        .customerId(
                                customer.id()
                        )
                        .insurancePlan(plan)
                        .startDate(startDate)
                        .endDate(endDate)
                        .status(
                                PolicyStatus.PAYMENT_PENDING
                        )
                        .build();

        Policy savedPolicy =
                policyRepository.save(policy);

        /*
         * Create first premium.
         */
        Premium premium =
                Premium.builder()
                        .policy(savedPolicy)
                        .amount(annualPremium)
                        .dueDate(startDate)
                        .paid(false)
                        .build();

        premiumRepository.save(premium);

        log.info(
                "Policy created successfully: policyId={}",
                savedPolicy.getId()
        );

        return buildResponse(
                savedPolicy,
                annualPremium
        );
    }


    /**
     * Illustrative premium calculation.
     *
     * This is NOT a real insurance underwriting formula.
     */
    private BigDecimal calculatePremium(
            InsurancePlan plan,
            LocalDate dateOfBirth) {

        int age =
                Period.between(
                        dateOfBirth,
                        LocalDate.now()
                ).getYears();

        BigDecimal multiplier;

        if (age <= 30) {

            multiplier =
                    new BigDecimal("1.00");

        } else if (age <= 45) {

            multiplier =
                    new BigDecimal("1.15");

        } else if (age <= 60) {

            multiplier =
                    new BigDecimal("1.35");

        } else {

            multiplier =
                    new BigDecimal("1.60");
        }

        return plan.getBasePremium()
                .multiply(multiplier)
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                );
    }


    /**
     * Get policy by ID.
     *
     * Phase 8:
     * Result is cached in Redis.
     *
     * Cache:
     * policies
     *
     * Key:
     * policy ID
     */
    @Transactional(readOnly = true)
    @Cacheable(
            value = "policies",
            key = "#id"
    )
    public PolicyResponse getPolicyById(
            Long id) {

        log.info(
                "Fetching policy from database: policyId={}",
                id
        );

        Policy policy =
                policyRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Policy not found with id: "
                                                + id
                                )
                        );

        BigDecimal premium =
                getAnnualPremium(policy);

        return buildResponse(
                policy,
                premium
        );
    }


    /**
     * Get all policies for a customer.
     *
     * Phase 8:
     * Result is cached in Redis.
     */
    @Transactional(readOnly = true)
    @Cacheable(
            value = "customerPolicies",
            key = "#customerId"
    )
    public List<PolicyResponse> getPoliciesByCustomer(
            Long customerId) {

        log.info(
                "Fetching policies from database: customerId={}",
                customerId
        );

        return policyRepository
                .findByCustomerId(customerId)
                .stream()
                .map(policy ->
                        buildResponse(
                                policy,
                                getAnnualPremium(policy)
                        )
                )
                .toList();
    }


    /**
     * Get annual premium for policy.
     */
    private BigDecimal getAnnualPremium(
            Policy policy) {

        return premiumRepository
                .findByPolicyId(policy.getId())
                .stream()
                .findFirst()
                .map(Premium::getAmount)
                .orElse(BigDecimal.ZERO);
    }


    /**
     * Build policy response.
     */
    private PolicyResponse buildResponse(
            Policy policy,
            BigDecimal annualPremium) {

        InsurancePlan plan =
                policy.getInsurancePlan();

        return PolicyResponse.builder()
                .id(policy.getId())
                .customerId(
                        policy.getCustomerId()
                )
                .insurancePlanId(
                        plan.getId()
                )
                .insurancePlanName(
                        plan.getName()
                )
                .coverageAmount(
                        plan.getCoverageAmount()
                )
                .startDate(
                        policy.getStartDate()
                )
                .endDate(
                        policy.getEndDate()
                )
                .status(
                        policy.getStatus()
                )
                .annualPremium(
                        annualPremium
                )
                .createdAt(
                        policy.getCreatedAt()
                )
                .build();
    }



    /**
     * Get all policies.
     *
     * Phase 8:
     * Result is cached in Redis.
     */
    @Transactional(readOnly = true)
    @Cacheable(
            value = "allPolicies",
            key = "'all'"
    )
    public List<PolicyResponse> getAllPolicies() {

        log.info("Fetching all policies from database");

        return policyRepository.findAll()
                .stream()
                .map(policy ->
                        buildResponse(
                                policy,
                                getAnnualPremium(policy)
                        )
                )
                .toList();
    }
}