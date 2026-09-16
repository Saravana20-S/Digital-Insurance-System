package com.insurance.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Customer entity.
 *
 * Stores customer profile information.
 *
 * Database:
 * insurance_auth_db
 *
 * Table:
 * customers
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Customer's first name.
     */
    @Column(nullable = false, length = 100)
    private String firstName;

    /**
     * Customer's last name.
     */
    @Column(nullable = false, length = 100)
    private String lastName;

    /**
     * Date of birth.
     */
    private LocalDate dateOfBirth;

    /**
     * Phone number.
     */
    @Column(length = 20)
    private String phoneNumber;

    /**
     * Customer address.
     */
    @Column(length = 500)
    private String address;

    /**
     * Link between Customer and User.
     *
     * One User has one Customer profile.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    /**
     * Customer creation time.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Automatically set creation timestamp.
     */
    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}