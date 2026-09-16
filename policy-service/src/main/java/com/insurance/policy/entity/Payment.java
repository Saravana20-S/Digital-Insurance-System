package com.insurance.policy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payment entity.
 *
 * Payment is simulated in this training project.
 *
 * No real banking/payment gateway is integrated.
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Policy associated with this payment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "policy_id",
            nullable = false
    )
    private Policy policy;

    /**
     * Payment amount.
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal amount;

    /**
     * Current payment status.
     */
    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    /**
     * Simulated transaction reference.
     */
    @Column(length = 100)
    private String transactionReference;

    /**
     * Payment creation timestamp.
     */
    @Column(nullable = false)
    private LocalDateTime paymentDate;

    /**
     * Automatically set defaults.
     */
    @PrePersist
    protected void onCreate() {

        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }

        if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }
}