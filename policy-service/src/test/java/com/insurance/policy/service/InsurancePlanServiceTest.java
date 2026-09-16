package com.insurance.policy.service;

import com.insurance.policy.dto.plan.InsurancePlanRequest;
import com.insurance.policy.dto.plan.InsurancePlanResponse;
import com.insurance.policy.entity.InsurancePlan;
import com.insurance.policy.repository.InsurancePlanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for InsurancePlanService.
 */
@SpringBootTest
@ActiveProfiles("test")
class InsurancePlanServiceTest {

    @Autowired
    private InsurancePlanService insurancePlanService;

    @Autowired
    private InsurancePlanRepository insurancePlanRepository;

    @Test
    void shouldCreateInsurancePlan() {

        InsurancePlanRequest request =
                InsurancePlanRequest.builder()
                        .name("Secure Life Plan")
                        .description(
                                "Complete life insurance protection"
                        )
                        .coverageAmount(
                                new BigDecimal("1000000")
                        )
                        .basePremium(
                                new BigDecimal("12000")
                        )
                        .durationYears(20)
                        .active(true)
                        .build();

        InsurancePlanResponse savedPlan =
                insurancePlanService.createPlan(request);

        assertNotNull(savedPlan);
        assertNotNull(savedPlan.getId());

        assertEquals(
                "Secure Life Plan",
                savedPlan.getName()
        );

        assertEquals(
                new BigDecimal("1000000"),
                savedPlan.getCoverageAmount()
        );

        assertEquals(
                new BigDecimal("12000"),
                savedPlan.getBasePremium()
        );
    }

    @Test
    void shouldFindInsurancePlanById() {

        InsurancePlan plan =
                InsurancePlan.builder()
                        .name("Family Protection Plan")
                        .description(
                                "Family life insurance"
                        )
                        .coverageAmount(
                                new BigDecimal("2000000")
                        )
                        .basePremium(
                                new BigDecimal("20000")
                        )
                        .durationYears(25)
                        .active(true)
                        .build();

        InsurancePlan saved =
                insurancePlanRepository.save(plan);

        InsurancePlanResponse found =
                insurancePlanService.getPlanById(
                        saved.getId()
                );

        assertNotNull(found);

        assertEquals(
                saved.getId(),
                found.getId()
        );

        assertEquals(
                "Family Protection Plan",
                found.getName()
        );
    }

    @Test
    void shouldGetAllInsurancePlans() {

        InsurancePlan plan =
                InsurancePlan.builder()
                        .name("Premium Life Plan")
                        .description("Premium coverage")
                        .coverageAmount(
                                new BigDecimal("5000000")
                        )
                        .basePremium(
                                new BigDecimal("50000")
                        )
                        .durationYears(30)
                        .active(true)
                        .build();

        insurancePlanRepository.save(plan);

        assertFalse(
                insurancePlanService
                        .getAllPlans()
                        .isEmpty()
        );
    }

    @Test
    void shouldUpdateInsurancePlan() {

        InsurancePlan plan =
                InsurancePlan.builder()
                        .name("Old Plan")
                        .description("Old description")
                        .coverageAmount(
                                new BigDecimal("1000000")
                        )
                        .basePremium(
                                new BigDecimal("10000")
                        )
                        .durationYears(20)
                        .active(true)
                        .build();

        InsurancePlan saved =
                insurancePlanRepository.save(plan);

        InsurancePlanRequest request =
                InsurancePlanRequest.builder()
                        .name("Updated Plan")
                        .description("Updated description")
                        .coverageAmount(
                                new BigDecimal("2000000")
                        )
                        .basePremium(
                                new BigDecimal("20000")
                        )
                        .durationYears(25)
                        .active(true)
                        .build();

        InsurancePlanResponse updated =
                insurancePlanService.updatePlan(
                        saved.getId(),
                        request
                );

        assertEquals(
                "Updated Plan",
                updated.getName()
        );

        assertEquals(
                new BigDecimal("2000000"),
                updated.getCoverageAmount()
        );

        assertEquals(
                25,
                updated.getDurationYears()
        );
    }

    @Test
    void shouldDeleteInsurancePlan() {

        InsurancePlan plan =
                InsurancePlan.builder()
                        .name("Delete Plan")
                        .description("Plan to delete")
                        .coverageAmount(
                                new BigDecimal("500000")
                        )
                        .basePremium(
                                new BigDecimal("5000")
                        )
                        .durationYears(10)
                        .active(true)
                        .build();

        InsurancePlan saved =
                insurancePlanRepository.save(plan);

        Long id = saved.getId();

        insurancePlanService.deletePlan(id);

        assertFalse(
                insurancePlanRepository
                        .findById(id)
                        .isPresent()
        );
    }
}