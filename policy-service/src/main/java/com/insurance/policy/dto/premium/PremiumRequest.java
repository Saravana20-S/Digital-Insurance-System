package com.insurance.policy.dto.premium;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumRequest {

    @NotNull
    private Long planId;

    @Min(18)
    @Max(100)
    private Integer customerAge;
}