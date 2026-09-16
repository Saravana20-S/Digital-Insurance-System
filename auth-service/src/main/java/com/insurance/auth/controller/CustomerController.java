package com.insurance.auth.controller;

import com.insurance.auth.dto.customer.CustomerRequest;
import com.insurance.auth.dto.customer.CustomerResponse;
import com.insurance.auth.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Customer operations.
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Create a new customer.
     *
     * POST /api/customers
     */
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response =
                customerService.createCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get all customers.
     *
     * GET /api/customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> customers =
                customerService.getAllCustomers();

        return ResponseEntity.ok(customers);
    }

    /**
     * Get customer by ID.
     *
     * GET /api/customers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable Long id) {

        CustomerResponse response =
                customerService.getCustomerById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Update customer.
     *
     * PUT /api/customers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response =
                customerService.updateCustomer(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete customer.
     *
     * DELETE /api/customers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}