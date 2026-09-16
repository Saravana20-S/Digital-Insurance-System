package com.insurance.policy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Insurance Policy entity.
 *
 * This entity belongs to policy-service.
 *
 * Customer information is represented using customerId
 * because Customer is owned by auth-service.
 */
@Entity
@Table(name = "policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID of customer from auth-service.
     *
     * This is NOT a JPA relationship.
     */
    @Column(nullable = false)
    private Long customerId;

    /**
     * Insurance plan selected by customer.
     *
     * InsurancePlan belongs to the same service/database,
     * therefore a JPA relationship is appropriate.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "plan_id",
            nullable = false
    )
    private InsurancePlan insurancePlan;

    /**
     * Policy start date.
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * Policy end date.
     */
    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * Current policy status.
     */
    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    @Builder.Default
    private PolicyStatus status = PolicyStatus.PAYMENT_PENDING;

    /**
     * Policy creation timestamp.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Automatically set defaults.
     */
    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (status == null) {
            status = PolicyStatus.PAYMENT_PENDING;
        }
    }
}