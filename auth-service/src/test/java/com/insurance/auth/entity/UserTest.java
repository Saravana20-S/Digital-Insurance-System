package com.insurance.auth.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User entity.
 *
 * This is a simple JUnit test.
 * No Spring context.
 * No database.
 * No Mockito.
 * No @DataJpaTest.
 */
class UserTest {

    @Test
    void shouldCreateUser() {

        // Create User object.
        User user = User.builder()
                .email("customer@gmail.com")
                .password("password123")
                .role(Role.CUSTOMER)
                .active(true)
                .build();

        // Verify object is created.
        assertNotNull(user);

        // Verify email.
        assertEquals(
                "customer@gmail.com",
                user.getEmail()
        );

        // Verify password.
        assertEquals(
                "password123",
                user.getPassword()
        );

        // Verify role.
        assertEquals(
                Role.CUSTOMER,
                user.getRole()
        );

        // Verify active status.
        assertTrue(user.getActive());
    }

    @Test
    void shouldCreateAdminUser() {

        User user = User.builder()
                .email("admin@gmail.com")
                .password("admin123")
                .role(Role.ADMIN)
                .active(true)
                .build();

        assertNotNull(user);

        assertEquals(
                Role.ADMIN,
                user.getRole()
        );

        assertTrue(user.getActive());
    }
}