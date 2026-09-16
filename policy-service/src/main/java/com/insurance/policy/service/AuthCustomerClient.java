package com.insurance.policy.service;

import com.insurance.policy.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Client used by policy-service to communicate with auth-service.
 */
@Service
@RequiredArgsConstructor
public class AuthCustomerClient {

    private final RestClient restClient;

    /**
     * Verify whether customer exists.
     */
    public CustomerDetails getCustomer(Long customerId) {

        try {

            CustomerDetails customer =
                    restClient.get()
                            .uri(
                                    "/api/customers/{id}",
                                    customerId
                            )
                            .retrieve()
                            .body(CustomerDetails.class);

            if (customer == null) {
                throw new ResourceNotFoundException(
                        "Customer not found with id: "
                                + customerId
                );
            }

            return customer;

        } catch (Exception exception) {

            throw new ResourceNotFoundException(
                    "Customer not found with id: "
                            + customerId
            );
        }
    }

    /**
     * Customer data received from auth-service.
     */
    public record CustomerDetails(
            Long id,
            String firstName,
            String lastName,
            String email,
            java.time.LocalDate dateOfBirth
    ) {
    }
}