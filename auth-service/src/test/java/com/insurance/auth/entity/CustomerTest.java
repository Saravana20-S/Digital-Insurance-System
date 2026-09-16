package com.insurance.auth.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Customer entity.
 *
 * Simple JUnit test.
 * No Spring.
 * No database.
 * No Mockito.
 */
class CustomerTest {

    @Test
    void shouldCreateCustomer() {

        // Create User.
        User user = User.builder()
                .email("customer@gmail.com")
                .password("password123")
                .role(Role.CUSTOMER)
                .active(true)
                .build();

        // Create Customer.
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("9876543210")
                .address("Bangalore")
                .user(user)
                .build();

        // Verify Customer object.
        assertNotNull(customer);

        assertEquals(
                "John",
                customer.getFirstName()
        );

        assertEquals(
                "Doe",
                customer.getLastName()
        );

        assertEquals(
                "9876543210",
                customer.getPhoneNumber()
        );

        assertEquals(
                "Bangalore",
                customer.getAddress()
        );

        // Verify User relationship.
        assertNotNull(customer.getUser());

        assertEquals(
                user,
                customer.getUser()
        );
    }
}