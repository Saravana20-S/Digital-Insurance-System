package com.insurance.policy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Premium entity.
 *
 * Represents premium amounts/due dates
 * associated with a policy.
 */
@Entity
@Table(name = "premiums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Premium {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Policy associated with this premium.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "policy_id",
            nullable = false
    )
    private Policy policy;

    /**
     * Premium amount.
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal amount;

    /**
     * Premium due date.
     */
    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * Whether the premium has been paid.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean paid = false;

    /**
     * Creation timestamp.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Set defaults before insertion.
     */
    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (paid == null) {
            paid = false;
        }
    }
}