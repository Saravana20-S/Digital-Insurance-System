package com.insurance.auth.service;

import com.insurance.auth.dto.customer.CustomerRequest;
import com.insurance.auth.dto.customer.CustomerResponse;
import com.insurance.auth.entity.Customer;
import com.insurance.auth.entity.Role;
import com.insurance.auth.entity.User;
import com.insurance.auth.repository.CustomerRepository;
import com.insurance.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for CustomerService.
 */
@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCreateCustomer() {

        User user =
                User.builder()
                        .email("phase3.customer@gmail.com")
                        .password("password123")
                        .role(Role.CUSTOMER)
                        .active(true)
                        .build();

        User savedUser =
                userRepository.save(user);

        CustomerRequest request =
                CustomerRequest.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .dateOfBirth(
                                LocalDate.of(
                                        1995,
                                        5,
                                        10
                                )
                        )
                        .phoneNumber("9876543210")
                        .address("Bangalore")
                        .userId(savedUser.getId())
                        .build();

        CustomerResponse customer =
                customerService.createCustomer(request);

        assertNotNull(customer);
        assertNotNull(customer.getId());

        assertEquals(
                "John",
                customer.getFirstName()
        );

        assertEquals(
                "Doe",
                customer.getLastName()
        );

    }

    @Test
    void shouldFindCustomerById() {

        User user =
                User.builder()
                        .email("phase3.find@gmail.com")
                        .password("password123")
                        .role(Role.CUSTOMER)
                        .active(true)
                        .build();

        User savedUser =
                userRepository.save(user);

        CustomerRequest request =
                CustomerRequest.builder()
                        .firstName("Jane")
                        .lastName("Smith")
                        .dateOfBirth(
                                LocalDate.of(
                                        1998,
                                        8,
                                        15
                                )
                        )
                        .phoneNumber("9876501234")
                        .address("Chennai")
                        .userId(savedUser.getId())
                        .build();

        CustomerResponse savedCustomer =
                customerService.createCustomer(request);

        CustomerResponse foundCustomer =
                customerService.getCustomerById(
                        savedCustomer.getId()
                );

        assertNotNull(foundCustomer);

        assertEquals(
                savedCustomer.getId(),
                foundCustomer.getId()
        );

        assertEquals(
                "Jane",
                foundCustomer.getFirstName()
        );
    }

    @Test
    void shouldUpdateCustomer() {

        User user =
                User.builder()
                        .email("phase3.update@gmail.com")
                        .password("password123")
                        .role(Role.CUSTOMER)
                        .active(true)
                        .build();

        User savedUser =
                userRepository.save(user);

        CustomerRequest createRequest =
                CustomerRequest.builder()
                        .firstName("Old Name")
                        .lastName("Doe")
                        .dateOfBirth(
                                LocalDate.of(
                                        1995,
                                        1,
                                        1
                                )
                        )
                        .phoneNumber("9876543210")
                        .address("Chennai")
                        .userId(savedUser.getId())
                        .build();

        CustomerResponse customer =
                customerService.createCustomer(
                        createRequest
                );

        CustomerRequest updateRequest =
                CustomerRequest.builder()
                        .firstName("Updated Name")
                        .lastName("Doe Updated")
                        .dateOfBirth(
                                LocalDate.of(
                                        1995,
                                        1,
                                        1
                                )
                        )
                        .phoneNumber("9999999999")
                        .address("Bangalore")
                        .userId(savedUser.getId())
                        .build();

        CustomerResponse updated =
                customerService.updateCustomer(
                        customer.getId(),
                        updateRequest
                );

        assertEquals(
                "Updated Name",
                updated.getFirstName()
        );

        assertEquals(
                "Doe Updated",
                updated.getLastName()
        );

        assertEquals(
                "Bangalore",
                updated.getAddress()
        );
    }

    @Test
    void shouldDeleteCustomer() {

        User user =
                User.builder()
                        .email("phase3.delete@gmail.com")
                        .password("password123")
                        .role(Role.CUSTOMER)
                        .active(true)
                        .build();

        User savedUser =
                userRepository.save(user);

        CustomerRequest request =
                CustomerRequest.builder()
                        .firstName("Delete")
                        .lastName("Customer")
                        .dateOfBirth(
                                LocalDate.of(
                                        1990,
                                        1,
                                        1
                                )
                        )
                        .phoneNumber("9000000000")
                        .address("Bangalore")
                        .userId(savedUser.getId())
                        .build();

        CustomerResponse customer =
                customerService.createCustomer(request);

        Long customerId =
                customer.getId();

        customerService.deleteCustomer(customerId);

        assertFalse(
                customerRepository
                        .findById(customerId)
                        .isPresent()
        );
    }
}