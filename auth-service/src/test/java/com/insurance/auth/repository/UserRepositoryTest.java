package com.insurance.auth.repository;

import com.insurance.auth.entity.Role;
import com.insurance.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByEmail() {

        User user = User.builder()
                .email("customer@gmail.com")
                .password("password123")
                .role(Role.CUSTOMER)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());

        User foundUser =
                userRepository.findByEmail("customer@gmail.com")
                        .orElseThrow();

        assertEquals("customer@gmail.com", foundUser.getEmail());
        assertEquals(Role.CUSTOMER, foundUser.getRole());
        assertTrue(foundUser.getActive());
    }
}