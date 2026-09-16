package com.insurance.policy.service;

import com.insurance.policy.entity.InsurancePlan;
import com.insurance.policy.repository.InsurancePlanRepository;
import com.insurance.policy.repository.PolicyRepository;
import com.insurance.policy.repository.PremiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private InsurancePlanRepository insurancePlanRepository;

    @Mock
    private PremiumRepository premiumRepository;

    @Mock
    private AuthCustomerClient authCustomerClient;

    @InjectMocks
    private PolicyService policyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindInsurancePlan() {

        InsurancePlan plan = InsurancePlan.builder()
                .id(1L)
                .name("Life Secure")
                .basePremium(new BigDecimal("10000"))
                .coverageAmount(new BigDecimal("500000"))
                .durationYears(10)
                .active(true)
                .build();

        when(insurancePlanRepository.findById(1L))
                .thenReturn(Optional.of(plan));

        InsurancePlan result =
                insurancePlanRepository.findById(1L).orElseThrow();

        assertEquals("Life Secure", result.getName());
        assertEquals(10, result.getDurationYears());
    }
}