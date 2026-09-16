package com.insurance.policy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Insurance plan entity.
 *
 * Stores the insurance products/plans
 * available to customers.
 */
@Entity
@Table(name = "insurance_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePlan {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the insurance plan.
     */
    @Column(
            nullable = false,
            length = 150
    )
    private String name;

    /**
     * Description of the plan.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Coverage amount.
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal coverageAmount;

    /**
     * Base premium amount.
     *
     * This is only an illustrative/training value.
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal basePremium;

    /**
     * Policy duration in years.
     */
    @Column(nullable = false)
    private Integer durationYears;

    /**
     * Whether the plan is currently available.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    /**
     * Creation timestamp.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Set default values before insert.
     */
    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (active == null) {
            active = true;
        }
    }
}