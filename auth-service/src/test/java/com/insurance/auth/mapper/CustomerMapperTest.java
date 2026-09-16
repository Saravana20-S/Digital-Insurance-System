package com.insurance.auth.mapper;

import com.insurance.auth.dto.customer.CustomerResponse;
import com.insurance.auth.entity.Customer;
import com.insurance.auth.entity.Role;
import com.insurance.auth.entity.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for CustomerMapper.
 */
class CustomerMapperTest {

    private final CustomerMapper mapper =
            new CustomerMapper();

    @Test
    void shouldConvertEntityToResponse() {

        User user =
                User.builder()
                        .id(1L)
                        .email("customer@gmail.com")
                        .password("password123")
                        .role(Role.CUSTOMER)
                        .active(true)
                        .build();

        Customer customer =
                Customer.builder()
                        .id(10L)
                        .firstName("John")
                        .lastName("Doe")
                        .phoneNumber("9876543210")
                        .address("Bangalore")
                        .user(user)
                        .build();

        CustomerResponse response =
                mapper.toResponse(customer);

        assertNotNull(response);

        assertEquals(
                10L,
                response.getId()
        );

        assertEquals(
                "John",
                response.getFirstName()
        );

        assertEquals(
                "Doe",
                response.getLastName()
        );

        assertEquals(
                "9876543210",
                response.getPhoneNumber()
        );

        assertEquals(
                1L,
                response.getUserId()
        );

        assertEquals(
                "customer@gmail.com",
                response.getEmail()
        );
    }
}