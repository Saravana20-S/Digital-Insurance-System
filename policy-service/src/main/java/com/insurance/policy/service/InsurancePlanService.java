package com.insurance.policy.service;

import com.insurance.policy.dto.plan.InsurancePlanRequest;
import com.insurance.policy.dto.plan.InsurancePlanResponse;
import com.insurance.policy.entity.InsurancePlan;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.mapper.InsurancePlanMapper;
import com.insurance.policy.repository.InsurancePlanRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for Insurance Plan operations.
 */
@Service
@RequiredArgsConstructor
public class InsurancePlanService {

    private final InsurancePlanRepository insurancePlanRepository;

    private final InsurancePlanMapper insurancePlanMapper;

    /**
     * Create a new insurance plan.
     */
    @CachePut(
            value = "insurancePlans",
            key = "#result.id"
    )
    public InsurancePlanResponse createPlan(
            InsurancePlanRequest request) {

        InsurancePlan plan =
                InsurancePlan.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .coverageAmount(
                                request.getCoverageAmount()
                        )
                        .basePremium(
                                request.getBasePremium()
                        )
                        .durationYears(
                                request.getDurationYears()
                        )
                        .active(
                                request.getActive() != null
                                        ? request.getActive()
                                        : true
                        )
                        .build();

        InsurancePlan savedPlan =
                insurancePlanRepository.save(plan);

        return insurancePlanMapper.toResponse(savedPlan);
    }

    /**
     * Get all insurance plans.
     */
    @Cacheable(value = "insurancePlans", key = "'all'")
    @Transactional(readOnly = true)
    public List<InsurancePlanResponse> getAllPlans() {

        System.out.println("Fetching from DB...");

        return insurancePlanRepository.findAll()
                .stream()
                .map(insurancePlanMapper::toResponse)
                .toList();
    }

    /**
     * Get insurance plan by ID.
     */
    @Cacheable(
            value = "insurancePlans",
            key = "#id"
    )
    @Transactional(readOnly = true)
    public InsurancePlanResponse getPlanById(
            Long id) {

        InsurancePlan plan =
                insurancePlanRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance plan not found with id: "
                                                + id
                                )
                        );

        return insurancePlanMapper.toResponse(plan);
    }

    /**
     * Update insurance plan.
     */
    @Caching(
            put = {
                    @CachePut(value = "insurancePlan", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "insurancePlans", allEntries = true)
            }
    )
    public InsurancePlanResponse updatePlan(
            Long id,
            InsurancePlanRequest request) {

        InsurancePlan plan =
                insurancePlanRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance plan not found with id: "
                                                + id
                                )
                        );

        plan.setName(request.getName());
        plan.setDescription(request.getDescription());
        plan.setCoverageAmount(
                request.getCoverageAmount()
        );
        plan.setBasePremium(
                request.getBasePremium()
        );
        plan.setDurationYears(
                request.getDurationYears()
        );

        if (request.getActive() != null) {
            plan.setActive(request.getActive());
        }

        InsurancePlan updatedPlan =
                insurancePlanRepository.save(plan);

        return insurancePlanMapper.toResponse(updatedPlan);
    }

    /**
     * Delete insurance plan.
     */

    @Caching(evict = {
            @CacheEvict(value = "insurancePlan", key = "#id"),
            @CacheEvict(value = "insurancePlans", allEntries = true)
    })
    public void deletePlan(Long id) {

        InsurancePlan plan =
                insurancePlanRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Insurance plan not found with id: "
                                                + id
                                )
                        );

        insurancePlanRepository.delete(plan);
    }
}