package com.insurance.policy.mapper;

import com.insurance.policy.dto.plan.InsurancePlanResponse;
import com.insurance.policy.entity.InsurancePlan;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for InsurancePlanMapper.
 */
class InsurancePlanMapperTest {

    private final InsurancePlanMapper mapper =
            new InsurancePlanMapper();

    @Test
    void shouldConvertEntityToResponse() {

        InsurancePlan plan =
                InsurancePlan.builder()
                        .name("Secure Life")
                        .description("Life protection")
                        .coverageAmount(
                                new BigDecimal("1000000")
                        )
                        .basePremium(
                                new BigDecimal("12000")
                        )
                        .durationYears(20)
                        .active(true)
                        .build();

        InsurancePlanResponse response =
                mapper.toResponse(plan);

        assertNotNull(response);

        assertEquals(
                "Secure Life",
                response.getName()
        );

        assertEquals(
                "Life protection",
                response.getDescription()
        );

        assertEquals(
                new BigDecimal("1000000"),
                response.getCoverageAmount()
        );

        assertEquals(
                new BigDecimal("12000"),
                response.getBasePremium()
        );

        assertEquals(
                20,
                response.getDurationYears()
        );

        assertTrue(response.getActive());
    }
}