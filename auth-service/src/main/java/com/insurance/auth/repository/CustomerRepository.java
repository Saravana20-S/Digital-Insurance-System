package com.insurance.auth.repository;

import com.insurance.auth.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Customer entity.
 */
public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserId(Long userId);
}