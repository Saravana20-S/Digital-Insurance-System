package com.insurance.auth.mapper;

import com.insurance.auth.dto.customer.CustomerResponse;
import com.insurance.auth.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * Mapper for Customer entity and DTO conversion.
 */
@Component
public class CustomerMapper {

    /**
     * Convert Customer entity to response DTO.
     */
    public CustomerResponse toResponse(
            Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .userId(
                        customer.getUser() != null
                                ? customer.getUser().getId()
                                : null
                )
                .email(
                        customer.getUser() != null
                                ? customer.getUser().getEmail()
                                : null
                )
                .createdAt(customer.getCreatedAt())
                .build();
    }
}